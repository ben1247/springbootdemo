package com.shuyun.sbd.utils.netty.protocol.server;

import com.shuyun.sbd.utils.netty.protocol.enums.MessageType;
import com.shuyun.sbd.utils.netty.protocol.struct.Header;
import com.shuyun.sbd.utils.netty.protocol.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Component: 服务端的心跳应答
 * Description:
 * Date: 16/9/2
 *
 * @author yue.zhang
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage)msg;
        // 返回心跳应答消息
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()){
            System.out.println("Receive client heart beat message : ---> " + message);
            NettyMessage heartBeat = buildHeatBeat();
            System.out.println("Send heart beat response message to client: ---> " + heartBeat);
            ctx.writeAndFlush(msg);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeatBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }


}
