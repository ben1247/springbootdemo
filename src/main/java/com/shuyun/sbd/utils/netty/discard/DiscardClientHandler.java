package com.shuyun.sbd.utils.netty.discard;

import com.shuyun.sbd.utils.netty.common.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/**
 * Component:
 * Description:
 * Date: 16/7/31
 *
 * @author yue.zhang
 */
public class DiscardClientHandler extends ChannelHandlerAdapter {

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        int req = (int)ctx.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).get();
//        ByteBuf buf = ctx.alloc().buffer().writeInt(req);
//        ctx.writeAndFlush(buf);
//    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        StringBuilder sb = new StringBuilder();
//        try{
//            while(buf.isReadable()){ // 这个低效的循环事实上可以简化为:System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII))
//                sb.append((char) buf.readByte());
//            }
//
//            ctx.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).set(sb.toString());
//
//            // 关闭服务端与客户端的channel
//            ctx.channel().close();
//            ctx.close();
//
//        }finally {
//            ReferenceCountUtil.release(msg); // 或者，你可以在这里调用in.release()。
//        }

        ctx.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).set(msg.toString());
//        ctx.channel().close();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
