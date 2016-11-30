package com.shuyun.sbd.utils.queue.heap;

/**
 * Component:
 * Description:
 * Date: 16/11/27
 *
 * @author yue.zhang
 */
public class HeapSorter {

    public static void heapSort(int [] array){
        // 构建堆
        System.out.println("堆排序: ");
        buildHeap(array);
        for(int i=0;i<array.length;i++){
            System.out.print(array[i]);
            System.out.print(" ");
        }
        System.out.println();


        for(int i = array.length - 1 ; i >= 1 ; i--){

            swap(array, 0, i);
            System.out.println("第" + i + "次swap: ");
            for(int j=0;j<array.length;j++){
                System.out.print(array[j]);
                System.out.print(" ");
            }
            System.out.println();


            heapify(array, 0, i);
            System.out.println("第" + i + "次heapify: ");
            for(int j=0;j<array.length;j++){
                System.out.print(array[j]);
                System.out.print(" ");
            }
            System.out.println();

        }


    }

    public static void buildHeap(int [] array){
        int n = array.length;
        for(int i = n / 2 - 1; i >= 0 ; i--){
            heapify(array,i,n);
        }
    }

    public static void heapify(int [] A , int idx, int max){
        int left = 2 * idx + 1; // 左孩子的下标（如果存在的话）
        int right = 2 * idx + 2; // 右孩子的下标（如果存在的话）
        int largest = 0;//寻找3个节点中最大值节点的下标

        if(left < max && A[left] > A[idx]){
            largest = left;
        }else{
            largest = idx;
        }

        if(right < max && A[right] > A[largest]){
            largest = right;
        }

        if(largest != idx){
            swap(A,largest,idx);
            heapify(A,largest,max);
        }
    }

    public static void swap(int [] array , int i , int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String [] args){
//        int[] a = {2,1,3,5,4,6,7,16,9,10,11,12,13,14,15,8};
        int[] a = {6,7,16,9,2};
        heapSort(a);
        System.out.println("排序后: ");
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]);
            System.out.print(" ");
        }

    }

}
