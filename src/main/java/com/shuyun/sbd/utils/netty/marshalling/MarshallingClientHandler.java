package com.shuyun.sbd.utils.netty.marshalling;

import com.shuyun.sbd.utils.netty.marshalling.pojo.SubscribeReq;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Component:
 * Description:
 * Date: 16/8/28
 *
 * @author yue.zhang
 */
public class MarshallingClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0 ; i < 10 ; i++){
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReq subReq(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setAddress("Shanghai LuWanQu");
        req.setPhoneNumber("139xxxxxx");
        req.setProductName("Netty Book For Marshalling");
        req.setSubReqID(i);
        req.setUserName("zhangyue");
        return req;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [ " + msg + " ] ");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
