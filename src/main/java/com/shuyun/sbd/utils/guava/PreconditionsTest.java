package com.shuyun.sbd.utils.guava;

import com.google.common.base.Preconditions;

/**
 * Component:
 * Description:
 * Date: 16/10/23
 *
 * @author yue.zhang
 */
public class PreconditionsTest {

    private static void checkArgument(int index,String error){
        Preconditions.checkArgument(index > 2,error,index);
    }

    public static void main(String [] args){
        int s = 1;

        try {
            checkArgument(s,"操你妈的，写的什么叼毛数字！ %s");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
