package com.shuyun.sbd.utils;

import java.util.Random;

/**
 * Component:
 * Description:
 * Date: 16/7/30
 *
 * @author yue.zhang
 */
public class Test {

    public static void main(String [] args){
//        int s = (int) (System.currentTimeMillis() / 1000L + 2208988800L);
//        System.out.println(s);

        // 延迟超时
        int sleepMs = 1000 * Math.max(1, new Random().nextInt(1 << (3 + 1)));

        System.out.println(sleepMs);
    }

}
