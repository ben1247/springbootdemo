package com.shuyun.sbd.utils.netty.protocol.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 16/8/28
 *
 * @author yue.zhang
 */
public class Header {

    // 消息校验码
    private int crcCode = 0xabef0101;

    // 消息长度
    private int length;

    // 会话ID
    private long sessionID;

    // 消息类型
    private byte type;

    // 消息优先级
    private byte priority;

    // 附件
    private Map<String,Object> attachment = new HashMap<>();

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Header{" +
                "attachment=" + attachment +
                ", crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", priority=" + priority +
                '}';
    }
}
