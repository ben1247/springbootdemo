package com.shuyun.sbd.utils.netty.marshalling;

import com.shuyun.sbd.utils.netty.marshalling.pojo.SubscribeReq;
import com.shuyun.sbd.utils.netty.marshalling.pojo.SubscribeResp;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Component:
 * Description:
 * Date: 16/8/28
 *
 * @author yue.zhang
 */
public class MarshallingServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq)msg;
        if("zhangyue".equalsIgnoreCase(req.getUserName())){
            System.out.println("Service accept client subscribe req: [" + req.toString() + " ]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeResp resp(int subReqID) {
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(subReqID);
        resp.setRespCode(0);
        resp.setDesc("Netty book order succeed 3 days later, sent to the designated address");
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
