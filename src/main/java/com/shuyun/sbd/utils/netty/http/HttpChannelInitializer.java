package com.shuyun.sbd.utils.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        // 解码器
        channel.pipeline().addLast(new HttpRequestDecoder());
        channel.pipeline().addLast(new HttpObjectAggregator(65536)); // 将多个请求转换成一个full http

        // 数据处理完之后进行编码
        channel.pipeline().addLast(new HttpResponseEncoder());

        channel.pipeline().addLast(new HttpServerHandler());
    }
}
