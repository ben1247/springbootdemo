package com.shuyun.sbd.utils.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Component: Handles a server-side channel.
 * Description:
 *  DisCardServerHandler 继承自 ChannelHandlerAdapter，这个类实现了ChannelHandler接口，
 *  ChannelHandler提供了许多事件处理的接口方法，然后你可以覆盖这些方法。
 *  现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法
 *
 * Date: 16/7/25
 *
 * @author yue.zhang
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {

    /**
     * 这里我们覆盖了chanelRead()事件处理方法。
     * 每当从客户端收到新的数据时，这个方法会在收到消息时被调用，
     * 这个例子中，收到的消息的类型是ByteBuf
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*
            为了实现DISCARD协议，处理器不得不忽略所有接受到的消息。
            ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。
            请记住处理器的职责是释放所有传递到处理器的引用计数对象。
            通常，channelRead()方法的实现就像下面的这段代码：
                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) {
                    try {
                        // Do something with msg
                    } finally {
                        ReferenceCountUtil.release(msg);
                    }
                }
         */
        ByteBuf in = ((ByteBuf) msg);
        try{
            while(in.isReadable()){ // 这个低效的循环事实上可以简化为:System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII))
                System.out.print((char)in.readByte());
                System.out.flush();
            }
        }finally {
            ReferenceCountUtil.release(msg); // 或者，你可以在这里调用in.release()。
        }

    }

    /**
     * exceptionCaught()事件处理方法是当出现Throwable对象才会被调用，
     * 即当Netty由于IO错误或者处理器在处理事件时抛出的异常时。
     * 在大部分情况下，捕获的异常应该被记录下来并且把关联的channel给关闭掉。
     * 然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，
     * 比如你可能想在关闭连接之前发送一个错误码的响应消息。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
