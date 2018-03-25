package com.shuyun.sbd.utils.algorithm;

import java.util.Arrays;

/**
 * Component: 堆排序
 * Description:
 * Date: 16/4/19
 *
 * @author yue.zhang
 */
public class HeapSort {

    public static void maxHeapify(int [] arr, int size , int index){

        int leftSonIndex = 2 * index + 1;

        int rightSonIndex = 2 * index + 2;

        int tempIndex = index;

        if(index <= size / 2){


            // 比较父节点和左右子节点，拿最大的那个
            if(leftSonIndex < size && arr[tempIndex] < arr[leftSonIndex]){
                tempIndex = leftSonIndex;
            }
            if(rightSonIndex < size && arr[tempIndex] < arr[rightSonIndex]){
                tempIndex = rightSonIndex;
            }

            // 左右子节点的值存在比父节点的值更大
            if(tempIndex != index){
                swap(arr, index, tempIndex); // 交换值
                maxHeapify(arr, size, tempIndex); // 递归调整
            }
        }

    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void heapSort(int[] arr, int size) {
        // 构造成最大堆
        buildMaxHeap(arr, arr.length);
        for(int i = size - 1; i > 0; i --) {
            // 先交换堆顶元素和无序区最后一个元素
            swap(arr, 0, i);
            // 重新调整无序区
            buildMaxHeap(arr, i - 1);
        }
    }

    public static void buildMaxHeap(int[] arr, int size) {
        for(int i = size / 2; i >= 0; i --) { // 最后一个非叶子节点开始调整
            maxHeapify(arr, size, i);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 3, 5, 15, 9, 10, 1,4,2,13,78,13};
        System.out.println("before build: " + Arrays.toString(arr)); // before build: [3, 5, 15, 9, 10, 1]
        buildMaxHeap(arr, arr.length);
        System.out.println("after build: " + Arrays.toString(arr)); // after build: [15, 10, 3, 9, 5, 1]
        heapSort(arr, arr.length);
        System.out.println("after sort: " + Arrays.toString(arr)); // after sort: [1, 3, 5, 9, 10, 15]
    }



}
