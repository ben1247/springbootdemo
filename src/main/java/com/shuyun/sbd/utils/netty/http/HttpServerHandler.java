package com.shuyun.sbd.utils.netty.http;

import com.shuyun.sbd.utils.JsonUtil;
import com.shuyun.sbd.utils.netty.http.bean.RequestParam;
import com.shuyun.sbd.utils.netty.media.Media;
import com.shuyun.sbd.utils.netty.protobuf.RequestMsgProbuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.nio.charset.Charset;

/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
public class HttpServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        FullHttpRequest request = (FullHttpRequest)msg;

        ByteBuf buf = request.content();

        String req = buf.toString(Charset.forName("UTF-8"));

        RequestParam requestParam = JsonUtil.readValue(req, RequestParam.class);

        // 利用反射来执行controller的方法
        Object response = Media.execute(requestParam);

        ctx.writeAndFlush(response);
    }
}
