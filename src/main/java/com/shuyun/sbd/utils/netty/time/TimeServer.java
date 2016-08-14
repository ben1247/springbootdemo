package com.shuyun.sbd.utils.netty.time;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

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

        // 配置服务端的NI线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 1024)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel channel) throws Exception {

                     // LineBasedFrameDecoder的工作原理是它依次遍历ByteBuf中的可读字节，判断看是否有"\n" 或者 "\r\n"，
                     // 如果有，就以此位置为结束位置，从可读索引到结束位置区间的字节就组成了一行。它是以换行符为结束标志的解码器，
                     // 支持携带结束符或者不携带结束符两种解码方式，同时支持配置单行的最大长度。如果连续读取到最大长度后仍然没有发现换行符，
                     // 就会爆出异常，同时忽略掉之前都到的异常码流。
                     channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                     // StringDecoder的功能非常简单，就是将接收到的对象转换成字符串，然后继续调用后面的Handler,
                     // LineBasedFrameDecoder + StringDecoder 组合就是按行切换的文本解码器，它被设计用来支持TC的粘包和拆包。
                     channel.pipeline().addLast(new StringDecoder());
                     channel.pipeline().addLast(new TimeServerHandler());
                 }
             });

            // 绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();

            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();

        }finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String [] args) throws Exception {
        new TimeServer(8999).run();
    }
}
