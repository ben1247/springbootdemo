package com.shuyun.sbd.utils.maps;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Component: LinkedHashMap 的特点是put进去的对象位置未发生变化,而HashMap会发生变化.
 * Description:
 * Date: 16/12/10
 *
 * @author yue.zhang
 */
public class TestLinkedHashMap {

    public static void main(String [] args){
        System.out.println("=============LinkedHashMap=============");
        Map<Integer,String> map = new LinkedHashMap<>();
        map.put(6,"6666");
        map.put(5,"5555");
        map.put(4,"4444");
        map.put(3,"3333");
        map.put(2,"2222");
        map.put(1,"1111");

        for(Iterator it = map.keySet().iterator(); it.hasNext();){
            Object key = it.next();
            System.out.println(key + "=" + map.get(key));
        }

        System.out.println("=============HashMap=============");
        Map<Integer,String> map1 = new HashMap<>();
        map1.put(6,"6666");
        map1.put(5,"5555");
        map1.put(4,"4444");
        map1.put(3,"3333");
        map1.put(2,"2222");
        map1.put(1,"1111");

        for(Iterator it = map1.keySet().iterator(); it.hasNext();){
            Object key = it.next();
            System.out.println(key + "=" + map1.get(key));
        }

    }

}
