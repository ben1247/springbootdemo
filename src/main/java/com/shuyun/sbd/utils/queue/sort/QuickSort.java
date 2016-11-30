package com.shuyun.sbd.utils.queue.sort;

/**
 * Component: 快速排序
 * Description:
 * Date: 16/11/30
 *
 * @author yue.zhang
 */
public class QuickSort {

    public static void quick(int [] array){
        if(array.length > 0){
            quick(array,0,array.length-1);
        }
    }

    public static void quick(int [] array , int low , int high){
        if(low >= high){
            return;
        }
        int middle = getMiddle(array,low,high); // 将数组进行一分为二
        quick(array,low,middle - 1); // 对低数组进行递归排序
        quick(array,middle + 1 , high); // 对高数组进行递归排序
    }

    public static int getMiddle(int [] array , int low , int high){
        int temp = array[low]; // 数组得第一个作为中轴

        while (low < high){

            while (low < high && array[high] >= temp){
                high--;
            }

            array[low] = array[high]; // 比中轴小的记录移到低端

            while (low < high && array[low] <= temp){
                low ++;
            }

            array[high] = array[low]; // 比中轴大的记录移到高端
        }

        array[low] = temp; // 中轴记录到尾

        return low; // 返回中轴的位置
    }

    public static void main(String [] args){

        int [] array = {49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51};

        quick(array);

        for(int i=0;i<array.length;i++)

            System.out.print(array[i] + " ");
    }

}
