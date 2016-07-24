package com.shuyun.sbd.utils.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * Component:
 * Description:
 * Date: 16/7/24
 *
 * @author yue.zhang
 */
public class AioServerDemo {

    // 用来不让主程序退出，直到有异常情况
    private CountDownLatch latch;

    public void startServer() throws Exception {

        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();

        serverSocketChannel.bind(new InetSocketAddress(8999));

        latch = new CountDownLatch(1);

        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {


            @Override
            public void completed(AsynchronousSocketChannel channel, Void attachment) {
                try{
                    operate(channel);

                    serverSocketChannel.accept(attachment,this);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                latch.countDown();
            }
        });

        latch.await();
    }

    private void operate(AsynchronousSocketChannel channel) throws Exception {

        ByteBuffer buf = ByteBuffer.allocate(48);
        int size = channel.read(buf).get();
        while(size > 0){
            buf.flip();
            Charset charset = Charset.forName("UTF-8");
            System.out.println(charset.newDecoder().decode(buf).toString());
            size = channel.read(buf).get();
        }
        channel.close();
    }

    public static void main(String [] args) throws Exception {
        AioServerDemo serverDemo = new AioServerDemo();
        serverDemo.startServer();
    }

}
