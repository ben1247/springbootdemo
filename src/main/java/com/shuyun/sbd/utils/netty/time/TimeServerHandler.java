package com.shuyun.sbd.utils.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * Component:
 * Description:
 * Date: 16/7/28
 *
 * @author yue.zhang
 */
public class TimeServerHandler extends ChannelHandlerAdapter{

//    // channelActive()方法将会在连接被建立并且准备进行通信时被调用。因此让我们在这个方法里完成一个代表当前时间的32位整数消息的构建工作。
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        // 为了发送一个新的消息，我们需要分配一个包含这个消息的新的缓冲。因为我们需要写入一个32位的整数，因此我们需要一个至少有4个字节的ByteBuf。
//        // 通过ChannelHandlerContext.alloc()得到一个当前的ByteBufAllocator，然后分配一个新的缓冲。
//        final ByteBuf time = ctx.alloc().buffer(4);
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//
//        // ChannelHandlerContext.write()(和writeAndFlush())方法会返回一个ChannelFuture对象，一个ChannelFuture代表了一个还没有发生的I/O操作。
//        // 这意味着任何一个请求操作都不会马上被执行，因为在Netty里所有的操作都是异步的。举个例子下面的代码中在消息被发送之前可能会先关闭连接。
//        // Channel ch = ...;
//        // ch.writeAndFlush(message);
//        // ch.close();
//        // 因此你需要在write()方法返回的ChannelFuture完成后调用close()方法，然后当他的写操作已经完成他会通知他的监听者。
//        // 请注意,close()方法也可能不会立马关闭，他也会返回一个ChannelFuture。
//        ChannelFuture f = ctx.writeAndFlush(time);
//
//        // 当一个写请求已经完成是如何通知到我们？这个只需要简单地在返回的ChannelFuture上增加一个ChannelFutureListener。
//        // 这里我们构建了一个匿名的ChannelFutureListener类用来在操作完成时关闭Channel。或者，你可以使用简单的预定义监听器代码:
//        // f.addListener(ChannelFutureListener.CLOSE);
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                assert f == future;
//                ctx.close();
//            }
//        });
//
//    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte [] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"utf-8");
        System.out.println("The time server receive order: " + body);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
