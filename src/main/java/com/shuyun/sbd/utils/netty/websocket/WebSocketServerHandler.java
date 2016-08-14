package com.shuyun.sbd.utils.netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
public class WebSocketServerHandler extends ChannelHandlerAdapter {

    private WebSocketServerHandshaker handshaker ;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 建立连接
        if(msg instanceof FullHttpRequest){

            FullHttpRequest req = (FullHttpRequest)msg;

            // 解码是否正确
            if(!req.decoderResult().isSuccess() || !req.headers().get("Upgrade").equals("websocket")){
                // 输出 bad响应
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.BAD_REQUEST);
                ByteBuf buf = Unpooled.copiedBuffer("请求异常", CharsetUtil.UTF_8);
                response.content().writeBytes(buf);
                buf.release();

                ctx.writeAndFlush(response);
            }else{
                WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory("WS://127.0.0.1:8999/websocket",null,false);
                handshaker = handshakerFactory.newHandshaker(req);
                if(handshaker == null){
                    WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
                }
                handshaker.handshake(ctx.channel(),req);
            }



        }
        // 客户端和服务端进行通讯
        else if(msg instanceof WebSocketFrame){

            // 关闭消息
            if(msg instanceof CloseWebSocketFrame){
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg);
            }
            // 文本消息
            if(msg instanceof TextWebSocketFrame){
                String req = ((TextWebSocketFrame)msg).text();
                ctx.writeAndFlush(new TextWebSocketFrame(req + " 服务端返回数据"));
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
