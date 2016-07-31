package com.shuyun.sbd.utils.netty.discard;

import com.shuyun.sbd.utils.netty.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;


/**
 * Component:
 * Description:
 * Date: 16/7/31
 *
 * @author yue.zhang
 */
public class DiscardClient {

    private static Bootstrap b;

    private static PooledByteBufAllocator allocator;

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
                            ch.pipeline().addLast(new DiscardClientHandler());
                        }
                    });
            allocator = new PooledByteBufAllocator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object start(int obj) throws Exception{

        ChannelFuture f =  b.connect("localhost",8999).sync();

        // 向服务器写入数据
//        f.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).set(obj);
        ByteBuf buf = allocator.buffer().writeInt(obj);
        f.channel().writeAndFlush(buf);
        // 等待通道关闭，一般都是客户端可以关闭通道
        f.channel().closeFuture().sync();

        // 通道已关闭，返回通道的自定义属性值
        return f.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).get();

    }

}
