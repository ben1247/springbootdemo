package com.shuyun.sbd.utils.netty.protocol.codec;

import com.shuyun.sbd.utils.netty.protocol.struct.Header;
import com.shuyun.sbd.utils.netty.protocol.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Component: netty消息解码器
 * Description:
 *  LengthFieldBasedFrameDecoder 这个解码器支持自动的TC粘包和半包处理，
 *  只需要给出标识消息长度的字段偏移量和消息长度自身所占的字节数，
 *  Netty就能自动实现对半包的处理。对于业务解码器来说，调用父类LengthFieldBasedFrameDecoder的解码方法后，
 *  返回的就是整包消息或者为空。如果为空则说明是个半包消息，直接返回继续由I/O线程读取后续的码流。
 * Date: 16/8/28
 *
 * @author yue.zhang
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder{

    private MarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength , int lengthFieldOffset , int lengthFieldLength) throws IOException {
        super(maxFrameLength,lengthFieldOffset,lengthFieldLength);
        marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf)super.decode(ctx,in);
        if(frame == null){
            return null;
        }
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionID(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());

        int attachmentSize = frame.readInt();
        if(attachmentSize > 0){
            Map<String,Object> attch = new HashMap<>(attachmentSize);
            int keySize = 0;
            byte [] keyArray = null;
            String key = null;
            for(int i = 0 ; i < attachmentSize; i++){
                // 获取key值
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray,"utf-8");

                attch.put(key,marshallingDecoder.decode(frame));
            }
            keyArray = null;
            key = null;
            header.setAttachment(attch);
        }
        if(frame.readableBytes() > 4){
            message.setBody(marshallingDecoder.decode(frame));
        }
        message.setHeader(header);
        return message;
    }
}
