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

    public void connect(String host ,int port) throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{

            Bootstrap b = new Bootstrap();

            b.group(workerGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });

            // 我们用connect()方法代替了bind()方法。
            // start the client
            ChannelFuture f = b.connect(host,port).sync();

            // 等待客户端链路关闭
            f.channel().closeFuture().sync();

        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String [] args) throws Exception{
        String host = "127.0.0.1";
        int port = 8999;
        new TimeClient().connect(host,port);
    }

}
