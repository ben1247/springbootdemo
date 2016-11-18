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

    private final static String NODE_NAME = "n_";

    private static String getNodeNumber(String str , String nodeName){
        int index = str.lastIndexOf(nodeName);
        if(index >= 0){
            index += NODE_NAME.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }

    public static void main(String [] args){

        // 延迟超时
//        int sleepMs = 1000 * Math.max(1, new Random().nextInt(1 << (3 + 1)));
//        System.out.println(sleepMs);

        String str = "abcden_0001";
        String nodeName = NODE_NAME;

        System.out.println(getNodeNumber(str,nodeName));
    }

}
