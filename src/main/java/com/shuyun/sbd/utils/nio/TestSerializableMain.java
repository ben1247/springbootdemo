package com.shuyun.sbd.utils.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Component:
 * Description:
 * Date: 16/8/15
 *
 * @author yue.zhang
 */
public class TestSerializableMain {

    public static void main(String [] args) throws IOException {
        TestSerializable userInfo = new TestSerializable();
        userInfo.setUserId(100);
        userInfo.setUsername("netty");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(userInfo);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The JDK serializable length is : " + b.length);
        bos.close();
        System.out.println("-----------------------------------------------");
        System.out.println("The byteBuffer serializable length is : " + userInfo.codeC().length);
    }

}
