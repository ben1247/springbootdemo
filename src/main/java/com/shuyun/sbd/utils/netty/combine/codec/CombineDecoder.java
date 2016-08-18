package com.shuyun.sbd.utils.netty.combine.codec;

import com.shuyun.sbd.utils.netty.common.MyEncode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/8/16
 *
 * @author yue.zhang
 */
public class CombineDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        if(in.readableBytes() < 4){
            return;
        }

        int a = in.getUnsignedByte(in.readerIndex());
        int b = in.getUnsignedByte(in.readerIndex() + 1);

        if(isHttpRes(a,b)){
            // http
            ctx.pipeline().addBefore("CombineServerHandler","HttpServerCodec", new HttpServerCodec());
            ctx.pipeline().addBefore("CombineServerHandler","HttpObjectAggregator", new HttpObjectAggregator(65536));
        }else {
            // socket
            ctx.pipeline().addBefore("CombineServerHandler","DelimiterBasedFrameDecoder",new DelimiterBasedFrameDecoder(Integer.MAX_VALUE));
            ctx.pipeline().addBefore("CombineServerHandler","StringDecoder",new StringDecoder());
            ctx.pipeline().addBefore("CombineServerHandler","MyEncode",new MyEncode());
        }
        ctx.pipeline().remove(this);
    }

    private boolean isHttpRes(int a, int b) {
        boolean result = (a == 'G' && b == 'E' ||
                          a == 'P' && b == 'O' ||
                          a == 'P' && b == 'U' ||
                          a == 'H' && b == 'E' ||
                          a == 'O' && b == 'P' ||
                          a == 'P' && b == 'A' ||
                          a == 'D' && b == 'E' ||
                          a == 'T' && b == 'R' ||
                          a == 'C' && b == 'O');

        return result;

    }
}
