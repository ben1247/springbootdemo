package com.shuyun.sbd.utils.netty.http;

import com.shuyun.sbd.utils.JsonUtil;
import com.shuyun.sbd.utils.netty.common.Constant;
import com.shuyun.sbd.utils.netty.http.bean.RequestParam;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;

import java.net.URI;


/**
 * Component:
 * Description:
 * Date: 16/7/31
 *
 * @author yue.zhang
 */
public class HttpClient {

    private static Bootstrap b;

    static {
        try {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 编码
                            ch.pipeline().addLast(new HttpRequestEncoder());

                            ch.pipeline().addLast(new HttpObjectAggregator(65536));

                            ch.pipeline().addLast(new HttpResponseDecoder());

                            ch.pipeline().addLast(new HttpClientHandler());

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object start(RequestParam requestParam) throws Exception{

        // 绑定ip和端口
        ChannelFuture f =  b.connect("localhost",8999).sync();

        URI uri = new URI("http://127.0.0.1:8999");

        // 通信的内容
        ByteBuf content = Unpooled.wrappedBuffer(JsonUtil.writeValueAsString(requestParam).getBytes("utf-8"));

        DefaultFullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,uri.toASCIIString(),content);

        // 设置头信息
        req.headers().set(CONTENT_TYPE,"text/plain");
        req.headers().set(HOST,"127.0.0.1");
        req.headers().set(CONTENT_LENGTH, content.readableBytes());
        req.headers().set(CONNECTION,HttpHeaders.Values.KEEP_ALIVE);

        // 发送
        f.channel().writeAndFlush(req);

        // 等待通道关闭，一般都是客户端可以关闭通道
        f.channel().closeFuture().sync();

        // 通道已关闭，返回通道的自定义属性值
        return f.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).get();

    }

}
