package com.shuyun.sbd.utils.nio;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Component:
 * Description:
 * Date: 16/8/15
 *
 * @author yue.zhang
 */
public class TestSerializable implements Serializable {

    private static final long serialVersionUID = -8591166948231181448L;

    private String username;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte [] codeC(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte [] value = this.username.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte [] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public byte [] codeC(ByteBuffer buffer){
        buffer.clear();
        byte[] value = this.username.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte [] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
