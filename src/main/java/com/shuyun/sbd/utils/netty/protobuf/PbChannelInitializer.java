package com.shuyun.sbd.utils.netty.protobuf;

import com.shuyun.sbd.utils.netty.common.MyEncode;
import com.shuyun.sbd.utils.netty.discard.DiscardServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;


/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
public class PbChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        // 解码器
        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        channel.pipeline().addLast(new ProtobufDecoder(RequestMsgProbuf.RequestMsg.getDefaultInstance())); // 接收数据

        // 数据处理完之后进行编码
        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        channel.pipeline().addLast(new ProtobufEncoder());


        channel.pipeline().addLast(new PbServerHandler());
    }
}
