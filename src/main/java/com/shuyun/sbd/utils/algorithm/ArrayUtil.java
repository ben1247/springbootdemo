package com.shuyun.sbd.utils.algorithm;

/**
 * Component:
 * Description:
 * Date: 16/5/24
 *
 * @author yue.zhang
 */
public class ArrayUtil {

    public static void main(String [] args){

        int [] array = {1,2,3,4,5,6,7,8,9,10,11,12};

        int length = array.length;

        for(int i = 0 ; i < length / 2 ; i++){
            int temp = array[i];
            array[i] = array[length-1-i];
            array[length-1-i] = temp;
        }

        for(int i : array){
            System.out.println(i);
        }

    }

}
