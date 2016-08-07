package com.shuyun.sbd.utils.netty.protobuf;

import com.google.protobuf.ByteString;
import com.shuyun.sbd.utils.netty.protobuf.media.Media;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
public class PbServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 因为有解码器的缘故，所以这里可以强转为RequestMsg 对象（ channel.pipeline().addLast(new ProtobufDecoder(RequestMsgProbuf.RequestMsg.getDefaultInstance())); ）
        RequestMsgProbuf.RequestMsg requestMsg = (RequestMsgProbuf.RequestMsg)msg;

        String cmd = requestMsg.getCmd();

        System.out.println("cmd: " + cmd);

//        ByteString buf = requestMsg.getRequestParam();
//
//        UserProbuf.User user = UserProbuf.User.parseFrom(buf);
//
//        System.out.println("username: " + user.getUsername());
//
//        ctx.writeAndFlush(user); // 返回给客户端

        Object response = Media.execute(requestMsg);
        ctx.writeAndFlush(response);
    }
}
