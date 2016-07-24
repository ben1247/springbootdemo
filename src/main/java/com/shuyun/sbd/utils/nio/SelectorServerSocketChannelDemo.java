package com.shuyun.sbd.utils.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Component: 多路复用
 * Description:
 * Date: 16/7/23
 *
 * @author yue.zhang
 */
public class SelectorServerSocketChannelDemo {

    public void startServer() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8999));
        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){

            // 每一个链接只会select一次
            int select = selector.select();

            // 是否有可用的链接已接入
            if(select > 0){
                for(SelectionKey key : selector.selectedKeys()){
                    if(key.isAcceptable()){
                        SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
                        ByteBuffer readBuf = ByteBuffer.allocate(40);
                        int size = socketChannel.read(readBuf);
                        while (size > 0){
                            readBuf.flip(); //读模式转换写模式
                            Charset charset = Charset.forName("UTF-8");
                            System.out.println(charset.newDecoder().decode(readBuf).toString());

                            size = socketChannel.read(readBuf);
                        }

                        readBuf.clear();

                        ByteBuffer writeBuf = ByteBuffer.wrap("您好！我已经收到您的请求".getBytes());
                        socketChannel.write(writeBuf);
                        writeBuf.clear();
                        socketChannel.close();

                        selector.selectedKeys().remove(key);
                    }
                }
            }
        }
    }

    public static void main(String [] args) throws IOException {
        SelectorServerSocketChannelDemo server = new SelectorServerSocketChannelDemo();
        server.startServer();
    }

}
