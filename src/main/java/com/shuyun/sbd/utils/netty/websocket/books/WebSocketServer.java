package com.shuyun.sbd.utils.netty.websocket.books;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Component:
 * Description:
 * Date: 16/8/26
 *
 * @author yue.zhang
 */
public class WebSocketServer {

    public void run(int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 将请求和应答消息编码或者解码为http消息
                            ch.pipeline().addLast("http-codec",new HttpServerCodec());
                            // 目的是将http消息的多个部分组成一条完整的http消息
                            ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
                            // 来向客户端发送html5文件，它主要用于支持浏览器和服务端进行WebSocket通信
                            ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                            ch.pipeline().addLast("handler",new WebSocketServerHandler());
                        }
                    });

            Channel ch = b.bind(port).sync().channel();
            System.out.println("Web socket server started at port" + port + ".");
            System.out.println("Open your browser and navigate to http://127.0.0.1:" + port + "/");
            ch.closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String [] args) throws Exception {
        int port = 8999;
        if(args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        new WebSocketServer().run(port);
    }

}
