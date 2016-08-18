package com.shuyun.sbd.utils.netty.combine;

import com.shuyun.sbd.utils.netty.combine.codec.CombineDecoder;
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
public class CombineChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        channel.pipeline().addLast(new CombineDecoder());

        channel.pipeline().addLast("CombineServerHandler",new CombineServerHandler());
    }
}
