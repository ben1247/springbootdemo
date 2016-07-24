package com.shuyun.sbd.utils.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * Component:
 * Description:
 * Date: 16/7/24
 *
 * @author yue.zhang
 */
public class AioClientDemo {

    public void startClient() throws Exception {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        Future<?> connect = socketChannel.connect(new InetSocketAddress("localhost", 8999));
        connect.get();

        ByteBuffer buf = ByteBuffer.wrap("hello aio server, i am aio client".getBytes("UTF-8"));
        socketChannel.write(buf);

        buf.clear();
        socketChannel.close();
    }

    public static void main(String [] args) throws Exception {
        AioClientDemo clientDemo = new AioClientDemo();
        clientDemo.startClient();
    }

}
