package com.shuyun.sbd.demo.jvm;

/**
 * Component: volatile 变量自增运算测试，结果总数总是小于20000，优化请看：AtomicTest
 * Description:
 * Date: 16/3/16
 *
 * @author yue.zhang
 */
public class VolatileTest {

    public static volatile int race = 0;

    public static void increase(){
        race ++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String [] args){
        Thread [] threads = new Thread[THREADS_COUNT];
        for(int i = 0; i < THREADS_COUNT; i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++){
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        System.out.println(race);
    }

}
