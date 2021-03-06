package com.shuyun.sbd.utils.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Component: ProtoBuf 服务端
 * Description:
 * Date: 16/7/25
 *
 * @author yue.zhang
 */
//@Component
public class PbServer implements ApplicationListener<ContextRefreshedEvent>,Ordered{

    public void run(int port) throws Exception{

        /*
            配置服务端的nio线程组
            NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
            Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议。
            在这个例子中我们实现了一个服务端的应用，因此会有2个NioEventLoopGroup会被使用。
            第一个经常被叫做‘boss’，用来接收进来的连接。第二个经常被叫做‘worker’，用来处理已经被接收的连接，
            一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
            如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
            并且可以通过构造函数来配置他们的关系。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{

            // ServerBootstrap 是一个启动NIO服务的辅助启动类。你可以在这个服务中直接使用Channel，
            // 但是这会是一个复杂的处理过程，在很多情况下你并不需要这样做。
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class) // 这里我们指定使用NioServerSocketChannel类来举例说明一个新的Channel如何接收进来的连接。
                .option(ChannelOption.SO_BACKLOG, 1024) // 你可以设置这里指定的通道实现的配置参数。我们正在写一个TCP/IP的服务端，因此我们被允许设置socket的参数选项比如tcpNoDelay和keepAlive
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 你关注过option()和childOption()吗？option()是提供给NioServerSocketChannel用来接收进来的连接。childOption()是提供给由父管道ServerChannel接收到的连接，在这个例子中也是NioServerSocketChannel。
                .childHandler(new PbChannelInitializer());

            // 绑定端口，同步等待成功,返回的ChannelFuture，它的功能类似于JD的java.util.concurrent.Future,主要用于异步操作的通知回调
            ChannelFuture f = b.bind(port).sync(); // 我们继续，剩下的就是绑定端口然后启动服务。这里我们在机器上绑定了机器所有网卡上的8999端口。当然现在你可以多次调用bind()方法(基于不同绑定地址)。


            // 该方法进行阻塞，等待服务端链路关闭之后main函数才退出
            f.channel().closeFuture().sync();

        }finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            run(8999);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * 最简单的测试方法是用telnet 命令。例如，你可以在命令行上输入telnet localhost 8080或者其他类型参数。
     * @param args
     * @throws Exception
     */
    public static void main(String [] args) throws Exception {
        new PbServer().run(8999);
    }
}
