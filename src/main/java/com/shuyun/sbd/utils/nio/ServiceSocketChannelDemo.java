package com.shuyun.sbd.utils.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Component: 服务端
 * Description:
 * Date: 16/7/22
 *
 * @author yue.zhang
 */
public class ServiceSocketChannelDemo {


    public void startServer() throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8999));
        serverSocketChannel.configureBlocking(false); // 设置非阻塞

        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                ByteBuffer read = ByteBuffer.allocate(48);
                int size = socketChannel.read(read);
                while (size > 0){
                    read.flip();
                    Charset charset = Charset.forName("UTF-8");
                    System.out.println(charset.newDecoder().decode(read));

                    size = socketChannel.read(read);
                }
                read.clear();

                // 回复
                ByteBuffer write = ByteBuffer.wrap("hello 我已经接收到你的数据".getBytes());
                socketChannel.write(write);
                write.clear();

                socketChannel.close();
            }


        }

    }

    public static void main(String [] args) throws IOException {
        ServiceSocketChannelDemo serviceSocketChannelDemo = new ServiceSocketChannelDemo();
        serviceSocketChannelDemo.startServer();
    }

}
