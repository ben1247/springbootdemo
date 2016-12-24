package com.shuyun.sbd.utils.algorithm;

/**
 * Component: 空间换时间的排序方法
 * Description:
 * Date: 16/12/23
 *
 * @author yue.zhang
 */
public class SpaceToTime {

    public static void main(String [] args){
        int [] array = {5,6,3,2,8,7,1,9,4};

        int i;
        int max = array[0];
        int l = array.length;
        for(i = 1 ; i < l ; i++){
            if(array[i] > max){
                max = array[i];
            }
        }
        int [] temp = new int[max+1];
        for(i = 0 ; i < l ; i++){
            temp[array[i]] = array[i];
        }
        int j = 0;
        int max1 = max + 1;
        for(i = 0 ; i < max1; i++){
            if(temp[i] > 0){
                array[j++] = temp[i];
            }
        }


        for(i = 0 ; i < l ; i++){
            System.out.print(array[i] + " ");
        }
    }

}
