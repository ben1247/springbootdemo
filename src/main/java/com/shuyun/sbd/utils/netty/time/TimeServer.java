package com.shuyun.sbd.utils.netty.time;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Component:
 * Description:
 * Date: 16/7/28
 *
 * @author yue.zhang
 */
public class TimeServer {

    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public void run() throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer() {
                 @Override
                 protected void initChannel(Channel channel) throws Exception {
                     channel.pipeline().addLast(new TimeServerHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG,128)
             .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String [] args) throws Exception {
        new TimeServer(8999).run();
    }
}
