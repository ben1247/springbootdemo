package com.shuyun.sbd.utils.maps;

import java.util.*;

/**
 * Component:
 * Description:
 * Date: 16/12/28
 *
 * @author yue.zhang
 */
public class TestWeakHashMap {

    public static void testWeak(){
        Map map = new WeakHashMap();
        for(int i = 0 ; i < 10000; i++){
            Integer in = new Integer(i);
            map.put(in,new byte[i]);
        }
    }

    public static void testHash(){
        Map map = new HashMap();
        for(int i = 0 ; i < 10000; i++){
            Integer in = new Integer(i);
            map.put(in,new byte[i]);
        }
    }

    public static void main(String [] args){
        testWeak();
//        testHash();
    }

}
