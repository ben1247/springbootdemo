package com.shuyun.sbd.utils.netty.protobuf.subscribeBook;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Component:
 * Description:
 * Date: 16/8/18
 *
 * @author yue.zhang
 */
public class SubReqClient {

    public void connect(String host , int port) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        // ================== 解码 =================================

                        // 这是针对protobuf协议的ProtobufVarint32LengthFieldPrepender()所加的长度属性的解码器(主要用于半包处理)
                        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        ch.pipeline().addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));

                        // ================== 编码 =================================

                        // 对protobuf协议的的消息头上加上一个长度为32的整形字段，用于标志这个消息的长度。
                        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        ch.pipeline().addLast(new ProtobufEncoder());

                        // ================== 业务处理 =================================
                        ch.pipeline().addLast(new SubReqClientHandler());
                    }
                });

            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
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
        new SubReqClient().connect("127.0.0.1", port);
    }

}
