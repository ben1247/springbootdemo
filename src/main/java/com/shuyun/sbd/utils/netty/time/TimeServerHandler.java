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

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 没有解码器
//        ByteBuf buf = (ByteBuf) msg;
//        byte [] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req,"utf-8").substring(0,req.length - System.getProperty("line.separator").length());

        // 有解码器
        String body = (String) msg;
        System.out.println("The time server receive order: " + body + " ; the counter is : " + ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
