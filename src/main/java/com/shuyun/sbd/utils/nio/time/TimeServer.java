package com.shuyun.sbd.utils.nio.time;

/**
 * Component:
 * Description:
 * Date: 16/9/19
 *
 * @author yue.zhang
 */
public class TimeServer {

    public static void main(String [] args){
        int port = 8999;
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                // 采用默认值
            }
        }

        // 创建一个被称为MultiplexerTimeServer的多路复用类，它是一个独立的线程，负责轮询多路复用器Selector,可以处理多个客户端的并发接入
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);

        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
    }

}
