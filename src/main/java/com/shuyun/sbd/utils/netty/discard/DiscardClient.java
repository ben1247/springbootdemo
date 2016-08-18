package com.shuyun.sbd.utils.netty.discard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shuyun.sbd.utils.JsonUtil;
import com.shuyun.sbd.utils.netty.common.Constant;
import com.shuyun.sbd.utils.netty.common.MyEncode;
import com.shuyun.sbd.utils.netty.http.bean.RequestParam;
import com.shuyun.sbd.utils.netty.protobuf.user.Person;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
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
                            ch.pipeline().addLast(new MyEncode());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new DiscardClientHandler());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object start(Object obj) throws Exception{

        ChannelFuture f =  b.connect("localhost",8999).sync();

        // 向服务器写入数据
//        f.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).set(obj);
//        ByteBuf buf = allocator.buffer().writeInt(obj);
//        f.channel().writeAndFlush(buf);

        f.channel().writeAndFlush("client send " + obj);

        // 等待通道关闭，一般都是客户端可以关闭通道
        f.channel().closeFuture().sync();

        // 通道已关闭，返回通道的自定义属性值
        return f.channel().attr(AttributeKey.valueOf(Constant.ATTRIBUTE_KEY)).get();

    }

    public static void main(String [] args) throws Exception {

        Person person = new Person();
        person.setId("12");
        person.setUsername("zhangsan");

        RequestParam requestParam = new RequestParam();
        requestParam.setCommand("httpGetEmailByUser");
        requestParam.setParameter(JsonUtil.writeValueAsString(person));

        System.out.println(start(JsonUtil.writeValueAsString(requestParam)));
    }

}
