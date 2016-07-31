package com.shuyun.sbd.utils.netty.discard;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Component:
 * Description:
 * Date: 16/7/31
 *
 * @author yue.zhang
 */
public class DiscardClientTest extends TestCase {

    @Test
    public void test01() throws Exception {
        for(int i = 0 ; i < 10 ; i++){
            long st = System.currentTimeMillis();
            Object obj = DiscardClient.start("消息"+i);
            if(obj == null){
                throw new RuntimeException("返回数据为空");
            }
            long et = System.currentTimeMillis();
            System.out.println("第［" + i + "］循环，耗时：" + (et - st) + " ms , 收到服务器的反馈：" + obj);
        }
    }
}