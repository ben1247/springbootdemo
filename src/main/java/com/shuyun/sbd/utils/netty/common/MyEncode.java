package com.shuyun.sbd.utils.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/7/31
 *
 * @author yue.zhang
 */
public class MyEncode extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.getBytes("UTF-8"));
        out.writeBytes(Delimiters.lineDelimiter()[0]);
    }

}
