package com.shuyun.sbd.utils.netty.protocol.struct;

/**
 * Component: 心跳消息，握手请求和握手应答消息统一由NettyMessage承载
 * Description:
 * Date: 16/8/28
 *
 * @author yue.zhang
 */
public class NettyMessage {

    // 消息头
    private Header header;

    // 消息体
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "body=" + body +
                ", header=" + header +
                '}';
    }
}
