package com.shuyun.sbd.utils.zookeeper.zkclient.balance.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class ServerImpl implements Server {

    private EventLoopGroup boosGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private ServerBootstrap bootstrap = new ServerBootstrap();
    private ChannelFuture cf;
    private String zkAddress;
    private String serversPath;
    private String currentServerPath;
    private ServerData sd;

    private volatile boolean binded = false;

    private final ZkClient zkClient;
    private final RegistProvider registProvider;

    private static final Integer SESSION_TIME_OUT = 10000;
    private static final Integer CONNECT_TIME_OUT = 10000;

    public ServerImpl(String zkAddress , String serversPath , ServerData sd){
        this.zkAddress = zkAddress;
        this.serversPath = serversPath;
        this.sd = sd;
        this.zkClient = new ZkClient(zkAddress,SESSION_TIME_OUT,CONNECT_TIME_OUT,new SerializableSerializer());
        this.registProvider = new DefaultRegistProvider();

    }

    // 初始化服务端
    private void initRunning() throws Exception {
        String mePath = serversPath.concat("/").concat(sd.getPort().toString());
        //注册到zookeeper
        registProvider.regist(new ZooKeeperRegistContext(mePath, zkClient, sd));

        currentServerPath = mePath;
    }

    @Override
    public void bind() {
        if (binded){
            return;
        }
        System.out.println(sd.getPort() + ": binding...");

        try {
            initRunning();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        bootstrap.group(boosGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerHandler(new DefaultBalanceUpdateProvider(serversPath,zkClient)));
                    }
                });

        try {
            cf = bootstrap.bind(sd.getPort()).sync();
            binded = true;
            System.out.println(sd.getPort()+": binded...");
            cf.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public String getCurrentServerPath() {
        return currentServerPath;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public String getServersPath() {
        return serversPath;
    }

    public ServerData getSd() {
        return sd;
    }

    public void setSd(ServerData sd) {
        this.sd = sd;
    }

}
