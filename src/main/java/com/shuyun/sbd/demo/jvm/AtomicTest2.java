package com.shuyun.sbd.demo.jvm;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Component:
 * Description:
 * Date: 16/10/13
 *
 * @author yue.zhang
 */
public class AtomicTest2 {

    private static final ConcurrentHashMap<String, AtomicInteger> COUNT_MAP = new ConcurrentHashMap<>();

    public static String getResourceByRoundRobin(String name, List<String> slaveResources) {
        AtomicInteger count = COUNT_MAP.containsKey(name) ? COUNT_MAP.get(name) : new AtomicInteger(0);
        COUNT_MAP.putIfAbsent(name, count);
        count.compareAndSet(slaveResources.size(), 0);
        return slaveResources.get(count.getAndIncrement() % slaveResources.size());
    }

    public static void main(String[] args) throws InterruptedException {
        String name = "resource";
        List<String> slaveResources = Arrays.asList("r1", "r2", "r3", "r4");

        for (int i = 0; i < 10; i++) {

            System.out.println(getResourceByRoundRobin(name, slaveResources));
            Thread.sleep(2000);
        }


    }

}
