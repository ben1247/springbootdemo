package com.shuyun.sbd.utils;

import java.util.HashMap;
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

//        String str = "abcden_0001";
//        String nodeName = NODE_NAME;
//
//        System.out.println(getNodeNumber(str,nodeName));

//        String a = "aaa";
//        String a2= "aaa";
//        System.out.println(a.hashCode());
//        System.out.println(a2.hashCode());

        int number = 10;

        System.out.println(number);

        number = number << 1; // 10 * 2 = 20

        number = number << 4; // 20 * 2 * 2 * 2 * 2 = 320

        System.out.println(number);

        number = 10;

        number = number >> 1;  // 10 / 2 = 5

        System.out.println(number);

        number = 10;

        number = number >>> 1;

        System.out.println(number);

        int number1 = 10;
        int number2 = 10;

        number1 = number1 << 1;
        number2 <<= 1;
        System.out.println(number1);
        System.out.println(number2);


        HashMap<String,Integer> testHashMap = new HashMap<>();
        System.out.println(testHashMap.put("a",1)); // 应该返回oldValue
        System.out.println(testHashMap.put("a",2));

        int a = 5 & 6; // 将5和6转化成二进制，然后进行与运算，只要有一个为0 则为0

        System.out.println(a);

        System.out.println(5/2);
    }

    static int indexFor(int h, int length) {
        return h & (length-1);
    }


}
