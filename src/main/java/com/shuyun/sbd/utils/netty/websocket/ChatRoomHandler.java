package com.shuyun.sbd.utils.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * Component:
 * Description:
 * Date: 16/8/14
 *
 * @author yue.zhang
 */
public class ChatRoomHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String content = msg.text();
        Channel incoming = ctx.channel();
        for(Channel channel : channels){
            if(channel != incoming){
                channel.writeAndFlush(new TextWebSocketFrame(ctx.channel().remoteAddress() + " : " + content));
            }else {
                channel.writeAndFlush(new TextWebSocketFrame("我发送了：" + content));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        for(Channel channel : channels){
            channel.writeAndFlush(new TextWebSocketFrame(ctx.channel().remoteAddress() + " 已经进入聊天室"));
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        for(Channel channel : channels){
            channel.writeAndFlush(new TextWebSocketFrame(ctx.channel().remoteAddress() + " 离开了进入聊天室"));
        }
        channels.remove(ctx.channel());
    }
}
