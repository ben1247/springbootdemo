package com.shuyun.sbd.utils.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Component:
 * Description:
 * Date: 16/7/27
 *
 * @author yue.zhang
 */
public class EchoServerHandler extends ChannelHandlerAdapter{

    int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 如下代码用于使用DelimiterBasedFrameDecoder 解码器
        String body = (String)msg;
        System.out.println("This is " + ++counter + " times receive client : [" + body + "]");
        body += "$_";
        ByteBuf buf = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(buf);

        // 如下代码用于使用FixedLengthFrameDecoder 解码器, client 可以使用 telnet 127.0.0.1 8999 来发送消息
//        System.out.println("Receive client: [" + msg + "]");
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
