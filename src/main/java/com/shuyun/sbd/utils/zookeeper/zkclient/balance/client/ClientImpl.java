package com.shuyun.sbd.utils.zookeeper.zkclient.balance.client;

import com.shuyun.sbd.utils.zookeeper.zkclient.balance.server.ServerData;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class ClientImpl implements Client {

    private final BalanceProvider<ServerData> provider;

    private EventLoopGroup group = null;

    private Channel channel = null;

    public ClientImpl(BalanceProvider<ServerData> provider){
        this.provider = provider;
    }

    public BalanceProvider<ServerData> getProvider() {
        return provider;
    }

    @Override
    public void connect() throws Exception {
        try{

            ServerData serverData = provider.getBalanceItem();

            System.out.println("connecting to " + serverData.getHost() + ":" + serverData.getPort() + ", it's balance:" + serverData.getBalance());

            group = new NioEventLoopGroup();

            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });

            ChannelFuture f = b.connect(serverData.getHost(),serverData.getPort()).syncUninterruptibly();

            channel = f.channel();

            System.out.println("started success!");

        }catch (Exception e){
            System.out.println("连接异常:" + e.getMessage());
        }

    }

    @Override
    public void disConnect() throws Exception {
        try {
            if(channel != null){
                channel.close().syncUninterruptibly();
            }
            group.shutdownGracefully();
            group = null;
            System.out.println("disconnected");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
