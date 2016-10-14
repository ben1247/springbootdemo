package com.shuyun.sbd.utils.jvm;

import java.util.Vector;

/**
 * Component:
 * Description:
 * Date: 16/10/2
 *
 * @author yue.zhang
 */
public class OutOfMemoryTest {

    public static void main(String [] args){
        Vector v = new Vector();
        for(int i = 0 ; i < 25 ; i++){
            v.add(new byte[1024*1024]);
        }
    }

}
