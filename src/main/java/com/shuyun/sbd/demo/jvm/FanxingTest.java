package com.shuyun.sbd.demo.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 16/3/12
 *
 * @author yue.zhang
 */
public class FanxingTest {

    public static void main(String [] args){
        Map<String,String> map = new HashMap<String,String>();
        map.put("hello","你好");
        map.put("how are you?", "吃了没？");
        System.out.println(map.get("hello"));
        System.out.println(map.get("how are you?"));
    }
}
