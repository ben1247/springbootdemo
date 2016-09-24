package com.shuyun.sbd.utils.nio.time;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Component:
 * Description:
 * Date: 16/9/19
 *
 * @author yue.zhang
 */
public class MultiplexerTimeServer implements Runnable{

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器，绑定监听端口
     * @param port
     */
    public MultiplexerTimeServer(int port){
        try {
            selector = Selector.open(); // 创建多路复用器
            serverSocketChannel = ServerSocketChannel.open(); // 创建ServerSocketChannel
            serverSocketChannel.configureBlocking(false); // 设置为异步非阻塞模式
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // 资源初始化失败(例如端口被占用)，则退出
        }
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop){
            try{
                selector.select(1000);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()){
                    key = it.next();
                    it.remove();
                    try{
                        handleInput(key);
                    }catch (Exception e){
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }

                    }
                }
            }catch (Throwable t){
                t.printStackTrace();
            }
        }

        // 多路复用器关闭后，所以注册在上面的channel和pipe等资源都会被自动去注册和关闭，所以不需要重复释放资源
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            // 处理新接入的请求消息，完成这些操作后相当于完成了TCP的三次握手，TCP物理链路正式建立
            if(key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                SocketChannel sc =  ssc.accept(); // 通过ServerSocketChannel的accept接收客户端的连接请求并创建SocketChannel实例。
                sc.configureBlocking(false); // 将新创建的SocketChannel设置为异步非阻塞
                sc.register(selector,SelectionKey.OP_READ);
            }
            // 读取客户端的请求消息
            if (key.isReadable()){
                SocketChannel sc = (SocketChannel)key.channel();
                // 由于我们事先无法得知客户端发送的码流大小，开辟一个1mb的缓冲区。
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                // 调用SocketChannel的read方法读取请求码流。主意，由于我们将SocketChannel设置为异步非阻塞模式，因此它的read是非阻塞的。
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0){
                    readBuffer.flip(); // 作用是将缓冲区当前的limit设置为position, position设置为0，用于后续对缓冲区的读取操作。
                    byte [] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("The time server receive order : " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(sc,currentTime);
                }else if (readBytes < 0){
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                }else {
                    // 读到0字节，忽略
                }
            }
        }
    }

    /**
     * 需要指出的是，由于SocketChannel是异步非阻塞的，它并不保证一次能够把需要发送的字节数组发送完，此时会出现“写半包”问题。
     * 我们需要注册写操作，不断轮询Selector将没有发送完的ByteBuffer发送完毕，然后可以通过ByteBuffer的hasRemain()方法判断消息是否发送完成，
     * 这里没有处理写半包。
     * @param channel
     * @param response
     * @throws IOException
     */
    private void doWrite(SocketChannel channel , String response) throws IOException {
        if(response != null && response.trim().length() > 0){
            byte [] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}
