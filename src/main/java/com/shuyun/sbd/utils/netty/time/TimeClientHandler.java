package com.shuyun.sbd.utils.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * Component:
 * Description:
 * Date: 16/7/29
 *
 * @author yue.zhang
 */
public class TimeClientHandler extends ChannelHandlerAdapter{

    private ByteBuf buf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // ChannelHandler有2个生命周期的监听方法：handlerAdded()和handlerRemoved()。你可以完成任意初始化任务只要他不会被阻塞很长的时间。
        buf = ctx.alloc().buffer(4);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // ChannelHandler有2个生命周期的监听方法：handlerAdded()和handlerRemoved()。你可以完成任意初始化任务只要他不会被阻塞很长的时间。
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 在TCP/IP中，NETTY会把读到的数据放到ByteBuf的数据结构中。
        ByteBuf m = (ByteBuf) msg;

        // 首先，所有接收的数据都应该被累积在buf变量里。
        buf.writeBytes(m);

        m.release();

        if(buf.readableBytes() > 4){
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
