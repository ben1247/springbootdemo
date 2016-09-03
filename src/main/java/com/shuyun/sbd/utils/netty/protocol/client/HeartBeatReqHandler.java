package com.shuyun.sbd.utils.netty.protocol.client;

import com.shuyun.sbd.utils.netty.protocol.enums.MessageType;
import com.shuyun.sbd.utils.netty.protocol.struct.Header;
import com.shuyun.sbd.utils.netty.protocol.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Component: 客户端发送心跳请求消息
 * Description:
 * Date: 16/9/1
 *
 * @author yue.zhang
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter{

    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 握手成功，主动发送心跳消息
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            // 如果是握手成功消息，则启动无限循环定时器用于定期发送心跳消息。由于NioEventLoop是一个Schedule，因此它支持定时器的执行。
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HearBeatTask(ctx),0,5000, TimeUnit.MILLISECONDS);
        }else if(message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()){
            System.out.println("Client receive server heart beat message : --->" + message);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }

    private class HearBeatTask implements Runnable {

        private final ChannelHandlerContext ctx;

        public HearBeatTask(final ChannelHandlerContext ctx){
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage heatBeat = buildHeatBeat();
            System.out.println("Client send heart beat message to server: --> " + heatBeat);
            ctx.writeAndFlush(heatBeat);
        }

        private NettyMessage buildHeatBeat(){
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);
            return message;
        }
    }
}
