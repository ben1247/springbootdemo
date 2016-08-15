package com.shuyun.sbd.utils.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * Component: ECHO服务（响应式协议）
 * Description:
 * Date: 16/7/27
 *
 * @author yue.zhang
 */
public class EchoServer {

    public void run(int port) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{

            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());

                        // DelimiterBasedFrameDecoder的第一个参数1024表示单条消息的最大长度，当达到该长度后仍然没有查找到分隔符，就抛出TooLongFrameException异常，
                        // 防止由于异常码流缺失分隔符导致的内存溢出，这是netty解码器的可靠性保护。第二个参数就是分隔符缓冲对象。
                        channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter)); // 作用是防止粘包和拆包

                        // FixedLengthFrameDecoder是固定长度解码器，它能够按照制定的长度对消息进行自动解码，开发者不需要考虑TC的粘包和拆包问题。
//                        channel.pipeline().addLast(new FixedLengthFrameDecoder(20));

                        channel.pipeline().addLast(new StringDecoder()); // 作用是将byteBuf解码成字符串对象
                        channel.pipeline().addLast(new EchoServerHandler()); // 接收到的msg消息就是解码后的字符串对象
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
        new EchoServer().run(8999);
    }
}
