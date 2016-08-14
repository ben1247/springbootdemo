package com.shuyun.sbd.utils.netty.http;

import com.shuyun.sbd.utils.netty.common.Constant;
import com.shuyun.sbd.utils.netty.protobuf.ResponseMsgProbuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;

import java.nio.charset.Charset;

/**
 * Component:
 * Description:
 * Date: 16/8/4
 *
 * @author yue.zhang
 */
public class HttpClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 收到服务端的回复消息
        if(msg instanceof HttpResponse){
            HttpResponse httpResponse = (HttpResponse) msg;

            String contentType = httpResponse.headers().get(HttpHeaderNames.CONTENT_TYPE).toString();
//            System.out.println(contentType);
            ctx.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY2)).set(contentType);
        }else if(msg instanceof HttpContent){
            HttpContent httpContent = (HttpContent)msg;
            ByteBuf buf =  httpContent.content();

            // 存放到channel的属性中，供外边的调用者使用
            ctx.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).set(buf.toString(Charset.forName("UTF-8")));

            // 关闭channel
            ctx.channel().close();
        }



    }
}
