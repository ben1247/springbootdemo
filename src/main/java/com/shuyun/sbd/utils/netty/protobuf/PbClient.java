package com.shuyun.sbd.utils.netty.protobuf;

import com.shuyun.sbd.utils.netty.common.Constant;
import com.shuyun.sbd.utils.netty.common.MyEncode;
import com.shuyun.sbd.utils.netty.discard.DiscardClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.AttributeKey;


/**
 * Component:
 * Description:
 * Date: 16/7/31
 *
 * @author yue.zhang
 */
public class PbClient {

    private static Bootstrap b;

//    private static PooledByteBufAllocator allocator = new PooledByteBufAllocator();

    static {
        try {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 使用和protobuf相关的自4个解码和编码器

                            // 2个解码器
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            ch.pipeline().addLast(new ProtobufDecoder(UserProbuf.User.getDefaultInstance()));

                            // 2个编码器
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());

                            ch.pipeline().addLast(new PbClientHandler());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object start(RequestMsgProbuf.RequestMsg.Builder obj) throws Exception{

        ChannelFuture f =  b.connect("localhost",8999).sync();

        f.channel().writeAndFlush(obj);

        // 等待通道关闭，一般都是客户端可以关闭通道
        f.channel().closeFuture().sync();

        // 通道已关闭，返回通道的自定义属性值
        return f.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).get();

    }

}
