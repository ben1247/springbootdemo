package com.shuyun.sbd.utils.netty.http;

import com.shuyun.sbd.utils.JsonUtil;
import com.shuyun.sbd.utils.netty.http.bean.RequestParam;
import com.shuyun.sbd.utils.netty.media.Media;
import com.shuyun.sbd.utils.netty.protobuf.RequestMsgProbuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;

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
        Object resp = Media.execute(requestParam);

        // 将请求序列化成json对象
        String jsonp = JsonUtil.writeValueAsString(resp);

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(jsonp.getBytes("UTF-8")));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);

        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
