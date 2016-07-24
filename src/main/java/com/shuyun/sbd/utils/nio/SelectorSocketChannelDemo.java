package com.shuyun.sbd.utils.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Component:
 * Description:
 * Date: 16/7/24
 *
 * @author yue.zhang
 */
public class SelectorSocketChannelDemo {

    public void startClient() throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8999));
        socketChannel.configureBlocking(false);
        // 设置成非阻塞

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);


        ByteBuffer writeBuf = ByteBuffer.wrap("hello 我是客户端".getBytes("UTF-8"));
        socketChannel.write(writeBuf);
        writeBuf.clear();

        // 另外开启一个线程接收服务端返回的响应结果
        ClientThread responseThread = new ClientThread(selector);
        responseThread.start();

    }

    class ClientThread extends Thread{

        private Selector selector;

        public ClientThread(Selector selector){
            super();
            this.selector = selector;
        }

        @Override
        public void run() {

            try {

                while (selector.select() > 0){

                    for(SelectionKey key : selector.selectedKeys()){
                        SocketChannel socketChannel = (SocketChannel)key.channel();
                        ByteBuffer buf = ByteBuffer.allocate(40);
                        int size = socketChannel.read(buf);
                        while (size > 0){
                            buf.flip();
                            // 解码器
                            Charset charset = Charset.forName("UTF-8");
                            System.out.println(charset.newDecoder().decode(buf).toString());
                            size = socketChannel.read(buf);
                        }

                        selector.selectedKeys().remove(key);
                        socketChannel.close();
                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args) throws IOException {
        SelectorSocketChannelDemo selectorSocketChannel = new SelectorSocketChannelDemo();
        selectorSocketChannel.startClient();
    }
}
