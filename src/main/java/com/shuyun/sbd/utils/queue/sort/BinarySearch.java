package com.shuyun.sbd.utils.queue.sort;

/**
 * Component: 二分法查找
 * Description:
 * Date: 16/11/30
 *
 * @author yue.zhang
 */
public class BinarySearch {

    /**
     * 二分法查找
     * @param array 目标数组(已经排序好了)
     * @param T     查找的数
     * @return      目标数的索引
     */
    public static int binarySearch(int [] array , int T){
        int low , high , mid;
        low = 0;
        high = array.length - 1;

        int sortCount = 0;
        while (low <= high){
            sortCount ++;
            mid = ( low + high ) / 2;
            if(array[mid] < T){
                low = mid + 1;
            }else if(array[mid] > T){
                high = mid - 1;
            }else{
                System.out.println("使用了"+sortCount+"次排序");
                return mid;
            }
        }
        System.out.println("使用了"+sortCount+"次排序");
        return -1;
    }

    public static void main(String [] args){
        int [] array = {10,21,32,43,55,61,72,88,99,104,111,123,132,144,156,164,179,181};

        int T = 1233;

        System.out.println(binarySearch(array,T));

        // 此次排序最大次数为 5次
        double logRes = Math.log(1000000) / Math.log(2);
        System.out.println(logRes);


    }


}
