package com.shuyun.sbd.utils.netty.websocket.books;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Component:
 * Description:
 * Date: 16/8/26
 *
 * @author yue.zhang
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object>{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 传统的http接入：第一次握手请求消息由http协议承载，所以它是一个http消息，执行handleHttpRequest方法来处理WebSocket握手请求。
        if(msg instanceof FullHttpRequest){
            handleHttpRequest(ctx,(FullHttpRequest)msg);
        }
        // WebSocket接入
        else if(msg instanceof WebSocketFrame){
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // 如果http解码失败，返回http异常
        if(!req.getDecoderResult().isSuccess()
                || !"websocket".equals(req.headers().get("Upgrade"))){
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://127.0.0.1:8999/websocket",null,false);
        handshaker = wsFactory.newHandshaker(req);
        if(handshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }else {
            handshaker.handshake(ctx.channel(),req);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if(res.getStatus().code() != 200){
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res,res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        // 如果是非keep－alive，关闭连接
        if (!isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }

    }


    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否是关闭链路的指令
        if(frame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(),(CloseWebSocketFrame)frame.retain());
            return;
        }
        // 判断是否是ping消息
        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException(String.format("%s frame types not supported",frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame)frame).text();
        LOGGER.info(String.format("%s received %s",ctx.channel(),request));
        ctx.channel().write(new TextWebSocketFrame(request + ", 欢迎使用Netty WebSocket服务，现在时刻：" + new Date().toString()));
    }


    
    
}
