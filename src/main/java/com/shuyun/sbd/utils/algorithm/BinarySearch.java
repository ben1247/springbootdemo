package com.shuyun.sbd.utils.algorithm;

/**
 * Component: 二分查找
 * Description:
 * Date: 16/5/25
 *
 * @author yue.zhang
 */
public class BinarySearch {

    public static int rank(int key , int [] a){
        // 数组必须有序的
        int lo = 0;
        int hi = a.length - 1;
        while(lo <= hi){
            // 被查找的键要么不存在，要么必然存在于a[lo..hi]之中
            int mid = lo + (hi - lo) / 2;
            if(key < a[mid]){
                hi = mid - 1;
            }else if(key > a[mid]){
                lo = mid + 1;
            }else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String [] args){
        int [] array = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

    }
}
