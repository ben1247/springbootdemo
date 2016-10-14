package com.shuyun.sbd.utils.jvm;

/**
 * Component:
 * Description:
 *  XX:NewRatio=4表示年老代与年轻代的比值为4:1
 *  XX:SurvivorRatio的解释是Eden区与Survivor区的大小比值，-XX:SurvivorRatio=8表示Eden区与Survivor区的大小比值是8:1:1，因为Survivor区有两个
 * Date: 16/10/2
 *
 * @author yue.zhang
 */
public class GcTest {

    public static void main(String [] args){
        byte [] b = null;
        for(int i = 0 ; i < 10; i++){
            b = new byte[1024*1024];
        }
    }

}
