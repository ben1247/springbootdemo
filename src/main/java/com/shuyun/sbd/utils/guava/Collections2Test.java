package com.shuyun.sbd.utils.guava;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * Component:
 * Description:
 * Date: 16/10/25
 *
 * @author yue.zhang
 */
public class Collections2Test {

    public static void main(String [] args){
        List<String> list = Lists.newArrayList();

        Map map = new HashMap<>();
        Collections.synchronizedMap(map);
    }

}
