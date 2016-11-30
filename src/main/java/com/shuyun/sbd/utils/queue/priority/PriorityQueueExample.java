package com.shuyun.sbd.utils.queue.priority;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * Component: 优先队列
 * Description:
 * Date: 16/11/20
 *
 * @author yue.zhang
 */
public class PriorityQueueExample {

    private final static int queueCount = 7;

    public static void main(String [] args){

        // 优先队列自然排序示例
        Queue<Integer> integerPriorityQueue = new PriorityQueue<>(queueCount);
        Random rand = new Random();
        for(int i = 0 ; i < queueCount; i++){
            integerPriorityQueue.add(rand.nextInt(100));
        }
        for(int i = 0 ; i < queueCount; i++){
            Integer in = integerPriorityQueue.poll();
            System.out.println("Processing Integer:"+in);
        }

        // 优先队列使用示例
        Queue<Customer> customerPriorityQueue = new PriorityQueue<>(queueCount,idComparator);
        addDataToQueue(customerPriorityQueue);
        pollDataFromQueue(customerPriorityQueue);

    }

    public static Comparator<Customer> idComparator = new Comparator<Customer>(){
        @Override
        public int compare(Customer c1, Customer c2) {
            return c1.getId() - c2.getId();
        }
    };

    private static void addDataToQueue(Queue<Customer> customerPriorityQueue){
        Random rand = new Random();
        for(int i = 0 ; i < queueCount; i++){
            int id = rand.nextInt(100);
            customerPriorityQueue.add(new Customer(id , "ben"+id));
        }

    }

    private static void pollDataFromQueue(Queue<Customer> customerPriorityQueue){
        while (true){
            Customer c = customerPriorityQueue.poll();
            if(c == null){
                break;
            }
            System.out.println("Processing Customer with ID="+c.getId());
        }
    }

}
