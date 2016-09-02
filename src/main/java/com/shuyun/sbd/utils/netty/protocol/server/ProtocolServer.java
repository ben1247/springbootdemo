package com.shuyun.sbd.utils.netty.protocol.server;

import com.shuyun.sbd.utils.netty.protocol.codec.NettyMessageDecoder;
import com.shuyun.sbd.utils.netty.protocol.codec.NettyMessageEncoder;
import com.shuyun.sbd.utils.netty.protocol.common.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Component:
 * Description:
 * Date: 16/9/2
 *
 * @author yue.zhang
 */
public class ProtocolServer {

    public void bind() throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup,workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG,100)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("messageDecoder",new NettyMessageDecoder(1024*1024,4,4));
                    ch.pipeline().addLast("messageEncoder",new NettyMessageEncoder());
                    ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
                    ch.pipeline().addLast("loginAuthHandler",new LoginAuthRespHandler());
                    ch.pipeline().addLast("heartBeatHandler",new HeartBeatRespHandler());
                }
            });

        b.bind(Constant.REMOTEIP, Constant.PORT).sync();

        System.out.println("Netty Server start ok: " + Constant.REMOTEIP + ":" + Constant.PORT);

    }


    public static void main(String [] args){
        try {
            new ProtocolServer().bind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
