package com.shuyun.sbd.utils.netty.protobuf.subscribeBook;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Component:
 * Description:
 * Date: 16/8/18
 *
 * @author yue.zhang
 */
public class SubReqServer {

    public void bind(int port) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            // ================== 解码 =================================

                            // 这是针对protobuf协议的ProtobufVarint32LengthFieldPrepender()所加的长度属性的解码器(主要用于半包处理)
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            // 告诉ProtobufDecoder 需要解码的目标类是什么，否则仅仅从字节数组种是无法判断出要解码的目标类型信息的
                            ch.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));

                            // ================== 编码 =================================

                            // 对protobuf协议的的消息头上加上一个长度为32的整形字段，用于标志这个消息的长度。
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());

                            // ================== 业务处理 =================================
                            ch.pipeline().addLast(new SubReqServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

    public static void main(String [] args) throws InterruptedException {
        int port = 8999;
        if(args != null && args.length > 0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }
        new SubReqServer().bind(port);
    }

}
