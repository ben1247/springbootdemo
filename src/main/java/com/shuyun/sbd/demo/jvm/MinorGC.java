package com.shuyun.sbd.demo.jvm;

/**
 * Component: 新生代 Minor GC
 * vm 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * Description:
 * Date: 16/2/18
 *
 * @author yue.zhang
 */
public class MinorGC {

    private static final int _1MB = 1024 * 1024;

    public static void testAllocation(){
        byte [] allocaion1 , allocaion2 , allocaion3 , allocaion4;
        allocaion1 = new byte[2 * _1MB];
        allocaion2 = new byte[2 * _1MB];
        allocaion3 = new byte[2 * _1MB];
        allocaion4 = new byte[4 * _1MB]; // 出现一次Minor GC
    }

    public static void main(String [] args){
        MinorGC.testAllocation();
    }

}
