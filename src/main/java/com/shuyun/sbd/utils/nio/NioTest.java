package com.shuyun.sbd.utils.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannel;

import java.nio.channels.AsynchronousServerSocketChannel; // ServerSocket的aio版本，创建TCP服务端，绑定地址，监听端口等。

import java.nio.channels.AsynchronousSocketChannel; // 面向流的异步socket channel，表示一个连接。

/*
    异步channel的分组管理，目的是为了资源共享。一个AsynchronousChannelGroup绑定一个线程池，
    这个线程池执行两个任务：处理IO事件和派发CompletionHandler。
    AsynchronousServerSocketChannel创建的时候可以传入一个 AsynchronousChannelGroup，
    那么通过AsynchronousServerSocketChannel创建的
    AsynchronousSocketChannel将同属于一个组，共享资源。通过三个静态方法来创建：

    public static AsynchronousChannelGroup withFixedThreadPool(int nThreads, ThreadFactory threadFactory) throws IOException
    public static AsynchronousChannelGroup withCachedThreadPool(ExecutorService executor, int initialSize)
    public static AsynchronousChannelGroup withThreadPool(ExecutorService executor) throws IOException
*/
import java.nio.channels.AsynchronousChannelGroup;


/*
    异步IO操作结果的回调接口，用于定义在IO操作完成后所作的回调工作。
    AIO的API允许两种方式来处理异步操作的结果：返回的Future模式或者注册CompletionHandler，我更推荐用CompletionHandler的方式，
    这些handler的调用是由 AsynchronousChannelGroup的线程池派发的。显然，线程池的大小是性能的关键因素。
    AsynchronousChannelGroup允许绑定不同的线程池
*/
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Component:
 * Description:
 * Date: 15/9/1
 *
 * @author yue.zhang
 */
public class NioTest {

    private AsynchronousChannelGroup asynchronousChannelGroup;
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;
    private Future<AsynchronousSocketChannel> acceptFuture;
    private int threadPoolSize = 5;
    private boolean stared = false;


    public void createChannelGroup() throws IOException {
        if(asynchronousChannelGroup == null){
            this.asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(),threadPoolSize);
        }
    }

    public void createServerSocketChannel() throws IOException {
        if(asynchronousServerSocketChannel==null && this.asynchronousChannelGroup != null){
            this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open(this.asynchronousChannelGroup);

            // 通过nio 2.0引入的SocketOption类设置一些TCP选项：
            this.asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR,true);
            this.asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF,16*1024);

            // 绑定本地地址：
            this.asynchronousServerSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8081), 50);

        }
    }

    /*
    public void pendingAccept(){
        if(this.stared && this.asynchronousServerSocketChannel.isOpen()){
            this.acceptFuture = this.asynchronousServerSocketChannel.accept(null,new AcceptCompletionHandler());
        }else{
            throw new IllegalStateException("Controller has been closed");
        }
    }

    private final class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,Object> {

        @Override
        public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
            try {
                System.out.println(String.format("Accept connection from %s", socketChannel.getRemoteAddress()));
                configureChannel(socketChannel);
                AioSessionConfig sessionConfig = buildSessionConfig(socketChannel);
                Session session = new AioTCPSession(sessionConfig,
                        AioTCPController.this.configuration.getSessionReadBufferSize(),
                        AioTCPController.this.sessionTimeout);
                session.start();
                registerSession(session);
            }catch (Exception e) {
                e.printStackTrace();
                notifyException(e);
            }finally {
                pendingAccept();  // 继续发起accept调用
            }
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            try {
                notifyException(exc);
            } finally {
                pendingAccept(); // 继续发起accept调用
            }
        }


    }

    private class AioTCPSession {

        private ReadCompletionHandler readCompletionHandler;

        protected void read0() {
            pendingRead();
        }

        protected final void pendingRead() {
            if (!isClosed() && this.asynchronousSocketChannel.isOpen()) {
                if (!this.readBuffer.hasRemaining()) {
                    this.readBuffer = ByteBufferUtils
                            .increaseBufferCapatity(this.readBuffer);
                }
                this.readFuture = this.asynchronousSocketChannel.read(
                        this.readBuffer, this, this.readCompletionHandler);
            } else {
                throw new IllegalStateException(
                        "Session Or Channel has been closed");
            }
        }

        protected void write0(WriteMessage message) {
            boolean needWrite = false;
            synchronized (this.writeQueue) {
                needWrite = this.writeQueue.isEmpty();
                this.writeQueue.offer(message);
            }
            if (needWrite) {
                pendingWrite(message);
            }
        }

        protected final void pendingWrite(WriteMessage message) {
            message = preprocessWriteMessage(message);
            if (!isClosed() && this.asynchronousSocketChannel.isOpen()) {
                this.asynchronousSocketChannel.write(message.getWriteBuffer(),
                        this, this.writeCompletionHandler);
            } else {
                throw new IllegalStateException(
                        "Session Or Channel has been closed");
            }
        }

    }

    private final class ReadCompletionHandler implements CompletionHandler<Integer, AbstractAioSession> {


        protected final AioTCPController controller;

        public ReadCompletionHandler(AioTCPController controller) {
            this.controller = controller;
        }

        @Override
        public void cancelled(AbstractAioSession session) {
            System.out.println("Session(" + session.getRemoteSocketAddress() + ") read operation was canceled");
        }

        @Override
        public void completed(Integer result, AbstractAioSession session) {

            if (result < 0) {
                session.close();
                return;
            }
            try {
                if (result > 0) {
                    session.updateTimeStamp();
                    session.getReadBuffer().flip();
                    session.decode();
                    session.getReadBuffer().compact();
                }
            } finally {
                try {
                    session.pendingRead();
                } catch (IOException e) {
                    session.onException(e);
                    session.close();
                }
            }
            controller.checkSessionTimeout();
        }

        @Override
        public void failed(Throwable exc, AbstractAioSession session) {
            session.onException(exc);
            session.close();
        }

    }

    private final class WriteCompletionHandler implements CompletionHandler<Integer, AbstractAioSession>{

        @Override
        public void completed(Integer result, AbstractAioSession session) {
            WriteMessage writeMessage;
            Queue<WriteMessage> writeQueue = session.getWriteQueue();
            synchronized (writeQueue) {
                writeMessage = writeQueue.peek();
                if (writeMessage.getWriteBuffer() == null || !writeMessage.getWriteBuffer().hasRemaining()) {
                    writeQueue.remove();
                    if (writeMessage.getWriteFuture() != null) {
                        writeMessage.getWriteFuture().setResult(Boolean.TRUE);
                    }
                    try {
                        session.getHandler().onMessageSent(session,
                                writeMessage.getMessage());
                    } catch (Exception e) {
                        session.onException(e);
                    }
                    writeMessage = writeQueue.peek();
                }
            }
            if (writeMessage != null) {
                try {
                    session.pendingWrite(writeMessage);
                } catch (IOException e) {
                    session.onException(e);
                    session.close();
                }
            }
        }

        @Override
        public void failed(Throwable exc, AbstractAioSession session) {
            session.onException(exc);
            session.close();
        }
    }


    */

    public static void main(String [] args){
        byte [] s = {123, 34, 98, 117, 121, 101, 114, 95, 117, 115, 101, 114, 95, 105, 100, 34, 58, 52, 56, 44, 34, 98, 114, 105, 110, 103, 95, 103, 117, 105, 100, 101, 114, 95, 105, 100, 34, 58, 50, 51, 54, 44, 34, 98, 114, 97, 110, 100, 95, 105, 100, 34, 58, 49, 44, 34, 98, 117, 121, 101, 114, 95, 109, 101, 109, 111, 34, 58, 34, 34, 44, 34, 112, 97, 121, 95, 116, 121, 112, 101, 34, 58, 34, 87, 69, 73, 88, 73, 78, 34, 44, 34, 115, 104, 105, 112, 112, 105, 110, 103, 95, 116, 121, 112, 101, 34, 58, 34, 69, 88, 80, 82, 69, 83, 83, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 115, 116, 97, 116, 101, 34, 58, 34, -27, -116, -105, -28, -70, -84, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 115, 116, 97, 116, 101, 95, 99, 111, 100, 101, 34, 58, 34, 49, 49, 48, 48, 48, 48, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 99, 105, 116, 121, 34, 58, 34, -27, -116, -105, -28, -70, -84, -27, -72, -126, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 99, 105, 116, 121, 95, 99, 111, 100, 101, 34, 58, 34, 49, 49, 48, 49, 48, 48, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 100, 105, 115, 116, 114, 105, 99, 116, 34, 58, 34, -28, -72, -100, -27, -97, -114, -27, -116, -70, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 100, 105, 115, 116, 114, 105, 99, 116, 95, 99, 111, 100, 101, 34, 58, 34, 49, 49, 48, 49, 48, 49, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 97, 100, 100, 114, 101, 115, 115, 34, 58, 34, -27, -104, -69, -27, -104, -69, -27, -104, -69, -27, -104, -69, -27, -104, -69, -27, -104, -69, -27, -104, -69, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 110, 97, 109, 101, 34, 58, 34, -27, -83, -90, -28, -71, -96, -27, -83, -90, -28, -71, -96, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 122, 105, 112, 34, 58, 34, 34, 44, 34, 114, 101, 99, 101, 105, 118, 101, 114, 95, 109, 111, 98, 105, 108, 101, 34, 58, 34, 49, 51, 57, 49, 56, 54, 52, 56, 49, 57, 57, 34, 44, 34, 111, 114, 100, 101, 114, 115, 34, 58, 91, 123, 34, 115, 107, 117, 95, 105, 100, 34, 58, 49, 48, 49, 51, 57, 48, 44, 34, 105, 116, 101, 109, 95, 105, 100, 34, 58, 49, 48, 49, 48, 53, 53, 44, 34, 112, 114, 111, 112, 101, 114, 116, 105, 101, 115, 34, 58, 34, -26, -84, -66, -27, -68, -113, 58, -27, -92, -89, -26, -80, -108, 59, -23, -94, -100, -24, -119, -78, 58, -26, -87, -103, 34, 44, 34, 112, 114, 105, 99, 101, 34, 58, 48, 46, 48, 49, 44, 34, 114, 101, 97, 108, 95, 113, 117, 97, 110, 116, 105, 116, 121, 34, 58, 57, 52, 44, 34, 113, 117, 97, 110, 116, 105, 116, 121, 34, 58, 57, 57, 44, 34, 114, 101, 116, 97, 105, 110, 95, 113, 117, 97, 110, 116, 105, 116, 121, 34, 58, 53, 44, 34, 111, 117, 116, 101, 114, 95, 105, 100, 34, 58, 34, 48, 48, 49, 34, 44, 34, 111, 117, 116, 101, 114, 95, 115, 107, 117, 95, 105, 100, 34, 58, 34, 34, 44, 34, 105, 115, 95, 100, 101, 108, 101, 116, 101, 34, 58, 48, 44, 34, 99, 114, 101, 97, 116, 101, 100, 95, 97, 116, 34, 58, 34, 50, 48, 49, 53, 45, 48, 56, 45, 49, 56, 84, 49, 55, 58, 52, 49, 58, 53, 51, 43, 48, 56, 48, 48, 34, 44, 34, 117, 112, 100, 97, 116, 101, 100, 95, 97, 116, 34, 58, 34, 50, 48, 49, 53, 45, 48, 56, 45, 50, 49, 84, 49, 56, 58, 52, 48, 58, 50, 52, 43, 48, 56, 48, 48, 34, 44, 34, 112, 114, 105, 99, 101, 95, 100, 101, 115, 99, 34, 58, 34, -26, -118, -104, -26, -119, -93, -28, -69, -73, 34, 44, 34, 110, 117, 109, 34, 58, 49, 44, 34, 116, 105, 116, 108, 101, 34, 58, 34, 50, 48, 49, 53, -25, -89, -117, -27, -83, -93, -26, -106, -80, -27, -109, -127, -25, -119, -101, -28, -69, -108, -24, -93, -92, -27, -113, -111, -27, -72, -125, 34, 44, 34, 102, 114, 101, 105, 103, 104, 116, 34, 58, 48, 44, 34, 112, 105, 99, 85, 114, 108, 65, 114, 114, 34, 58, 91, 34, 104, 116, 116, 112, 58, 47, 47, 98, 114, 97, 110, 100, 45, 103, 117, 105, 100, 101, 46, 98, 48, 46, 117, 112, 97, 105, 121, 117, 110, 46, 99, 111, 109, 47, 102, 105, 108, 101, 45, 117, 112, 108, 111, 97, 100, 47, 49, 52, 51, 57, 55, 57, 53, 51, 54, 50, 55, 52, 48, 95, 52, 57, 53, 49, 57, 56, 46, 112, 110, 103, 34, 93, 44, 34, 98, 114, 97, 110, 100, 95, 105, 100, 34, 58, 49, 44, 34, 98, 114, 105, 110, 103, 95, 103, 117, 105, 100, 101, 114, 95, 105, 100, 34, 58, 50, 51, 54, 125, 93, 44, 34, 112, 114, 111, 109, 111, 95, 99, 111, 100, 101, 95, 105, 100, 34, 58, 49, 57, 53, 125};
        System.out.println(new String(s));

//        for(;;){
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("ss");
//        }

    }

}
