package com.shuyun.sbd.utils.guava.immutableCollection;

import com.google.common.collect.ImmutableList;

/**
 * Component:
 * Description:
 * Date: 16/10/24
 *
 * @author yue.zhang
 */
public class ImmutableCollectionTest {

    public static void testImmutableList(){
        ImmutableList<String> nameList = ImmutableList.of("aaa","bbb","ccc");
        for(String name : nameList){
            System.out.println(name);
        }
    }

    public static void main(String [] args){
        testImmutableList();
    }

}
