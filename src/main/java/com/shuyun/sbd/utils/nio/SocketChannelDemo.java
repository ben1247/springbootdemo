package com.shuyun.sbd.utils.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Component: 客户端
 * Description:
 * Date: 16/7/22
 *
 * @author yue.zhang
 */
public class SocketChannelDemo {


    public void startClient() throws IOException, InterruptedException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8999));
//        socketChannel.configureBlocking(false); // 非阻塞

        String requestStr = "hello server socket channel";
        ByteBuffer write = ByteBuffer.wrap(requestStr.getBytes("UTF-8")); // 制定字符集
        socketChannel.write(write);

        ByteBuffer read = ByteBuffer.allocate(48);
        int size = socketChannel.read(read);
        while (size > 0){
            read.flip(); // 读与写进行切换
            Charset charset = Charset.forName("UTF-8");
            System.out.println(charset.newDecoder().decode(read));
            read.clear();

            size = socketChannel.read(read);
        }

        write.clear();
        read.clear();

        socketChannel.close();

    }

    public static void main(String [] args) throws IOException, InterruptedException {
        SocketChannelDemo socketChannelDemo = new SocketChannelDemo();
        socketChannelDemo.startClient();
    }


}
