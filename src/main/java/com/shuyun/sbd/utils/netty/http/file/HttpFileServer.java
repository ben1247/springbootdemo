package com.shuyun.sbd.utils.netty.http.file;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Component:
 * Description:
 * Date: 16/8/20
 *
 * @author yue.zhang
 */
public class HttpFileServer {

    private static final String host = "127.0.0.1";

    private static final String DEFAULT_URL = "/src/main/java/com/shuyun/sbd/utils/netty/http/file/";

    public void run (final int port , final String url) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            // HttpObjectAggregator： 作用是将多个消息转换为单一的FullHttpRequest或者FullHttpResponse,
                            // 原因是HTTP解码器在每个HTT消息中会生产多个消息对象
                            // （1）HttpRequest / HttpResponse
                            // （2）HttpContent
                            // （3）LastHttpContent
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            // HttpResponseEncoder：对http响应消息进行编码
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            // ChunkedWriteHandler：它的主要作用是支持异步发送大的码流（例如大的文件传输），但不占用过多的内存，防止发生Java内存溢出错误。
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            // 处理业务逻辑
                            ch.pipeline().addLast("httpFileServerHandler", new HttpFileServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(host,port).sync();
            System.out.println("HTTP 文件目录服务器启动，网址是：http://" + host + ":" + port + url);

            f.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String [] args) throws Exception{
        int port = 8999;
        if(args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        String url = DEFAULT_URL;
        if(args.length > 1){
            url = args[1];
        }
        new HttpFileServer().run(port,url);
    }
}
