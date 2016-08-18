package com.shuyun.sbd.utils.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.shuyun.sbd.utils.netty.protobuf.subscribeBook.SubscribeReqProto;
import junit.framework.TestCase;

/**
 * Component:
 * Description:
 * Date: 16/8/18
 *
 * @author yue.zhang
 */
public class SubscribeReqProtoTest extends TestCase {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte [] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeSeq(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("zhangyue");
        builder.setProductName("Netty Book");
        builder.setAddress("ShangHai LuWanQu");

        return builder.build();
    }

    public static void main(String [] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeSeq();
        System.out.println("Before encode: " + req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("After decode: " + req2.toString());
        System.out.println("Assert equal: -->" + req2.equals(req));
    }

}