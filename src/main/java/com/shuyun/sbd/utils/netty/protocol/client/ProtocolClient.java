package com.shuyun.sbd.utils.netty.protocol.client;

import com.shuyun.sbd.utils.netty.protocol.codec.NettyMessageDecoder;
import com.shuyun.sbd.utils.netty.protocol.codec.NettyMessageEncoder;
import com.shuyun.sbd.utils.netty.protocol.common.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Component: 客户端
 * Description:
 * Date: 16/9/2
 *
 * @author yue.zhang
 */
public class ProtocolClient {

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    EventLoopGroup group = new NioEventLoopGroup();

    public void connect(String host , int port) throws Exception {
        // 配置客户端NIO线程组
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // NettyMessageDecoder 用于消息解码，为了防止由于单条消息过大导致的内存溢出或者畸形码流导致解码错位引起内存分配失败，我们对单条消息最大长度进行了上限限制
                        ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024 , 4, 4));
                        ch.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
                        ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
                        ch.pipeline().addLast("LoginAuthHandler",new LoginAuthReqHandler());
                        ch.pipeline().addLast("HeartBeatHandler",new HeartBeatReqHandler());
                    }
                });

            // 发起异步连接操作，发起TCP连接的代码与之前的不同，
            // 这次我们绑定了本地端口，主要用于服务端重复登录保护，另外，从产品管理角度看，一般情况下不允许系统随便使用随机端口
//            ChannelFuture future = b.connect(
//                    new InetSocketAddress(host,port),
//                    new InetSocketAddress(Constant.LOCAL_IP,Constant.LOCAL_PORT)).sync();
            ChannelFuture future = b.connect(
                    new InetSocketAddress(host, port),
                    new InetSocketAddress(Constant.LOCAL_IP,
                            Constant.LOCAL_PORT)).sync();

            future.channel().closeFuture().sync();

        }finally {
            // 所有资源释放完成之后，清空资源，再次发起重连操作
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        try{
                            connect(Constant.REMOTEIP,Constant.PORT); // 发起重连操作
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String [] args){
        try {
            new ProtocolClient().connect(Constant.REMOTEIP,Constant.PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
