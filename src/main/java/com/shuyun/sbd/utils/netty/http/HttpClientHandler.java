package com.shuyun.sbd.utils.netty.http;

import com.shuyun.sbd.utils.netty.common.Constant;
import com.shuyun.sbd.utils.netty.protobuf.ResponseMsgProbuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

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
        ResponseMsgProbuf.ResponseMsg responseMsg = (ResponseMsgProbuf.ResponseMsg)msg;

        // 存放到channel的属性中，供外边的调用者使用
        ctx.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).set(responseMsg.getRespnoseParam());

        // 关闭channel
        ctx.channel().close();
    }
}
