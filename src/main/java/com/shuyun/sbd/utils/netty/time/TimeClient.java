package com.shuyun.sbd.utils.netty.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.channels.SocketChannel;

/**
 * Component:
 * Description:
 * Date: 16/7/29
 *
 * @author yue.zhang
 */
public class TimeClient {

    public static void main(String [] args) throws Exception{
        String host = "localhost";
        int port = 8999;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            // BootStrap和ServerBootstrap类似,不过他是对非服务端的channel而言，比如客户端或者无连接传输模式的channel。
            Bootstrap b = new Bootstrap();
            // 如果你只指定了一个EventLoopGroup，那他就会即作为一个‘boss’线程，也会作为一个‘workder’线程，尽管客户端不需要使用到‘boss’线程。
            b.group(workerGroup);
            // 代替NioServerSocketChannel的是NioSocketChannel,这个类在客户端channel被创建时使用。
            b.channel(NioSocketChannel.class);
            // 不像在使用ServerBootstrap时需要用childOption()方法，因为客户端的SocketChannel没有父channel的概念。
            b.option(ChannelOption.SO_KEEPALIVE, true);

            b.handler(new ChannelInitializer() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // 我们用connect()方法代替了bind()方法。
            // start the client
            ChannelFuture f = b.connect(host,port).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

        }finally {
            workerGroup.shutdownGracefully();
        }

    }

}
