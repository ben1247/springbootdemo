package com.shuyun.sbd.utils.netty.protobuf;

import com.shuyun.sbd.utils.netty.common.Constant;
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
public class PbClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UserProbuf.User user = (UserProbuf.User)msg;

        ctx.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).set(user);

        ctx.channel().close();
    }
}
