package com.shuyun.sbd.utils.netty.discard;

import com.shuyun.sbd.utils.netty.common.MyEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;


/**
 * Component:
 *  这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。
 *  ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel。
 *  也许你想通过增加一些处理类比如DiscardServerHandle来配置一个新的Channel或者其对应的ChannelPipeline来实现你的网络程序。
 *  当你的程序变的复杂时，可能你会增加更多的处理类到pipline上，然后提取这些匿名类到最顶层的类上。
 * Description:
 * Date: 16/7/31
 *
 * @author yue.zhang
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        // 解码器
        channel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));

        // 转换成字符串
        channel.pipeline().addLast(new StringDecoder());

        // 数据处理完之后进行编码
        channel.pipeline().addLast(new MyEncode());

        channel.pipeline().addLast(new DiscardServerHandler());
    }
}
