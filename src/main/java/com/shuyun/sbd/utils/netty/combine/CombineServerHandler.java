package com.shuyun.sbd.utils.netty.combine;

import com.shuyun.sbd.utils.JsonUtil;
import com.shuyun.sbd.utils.netty.http.bean.RequestParam;
import com.shuyun.sbd.utils.netty.media.Media;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;

/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
public class CombineServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 利用反射来执行controller的方法
        Object resp = Media.execute(msg);

        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
