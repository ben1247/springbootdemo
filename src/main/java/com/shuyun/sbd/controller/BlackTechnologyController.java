package com.shuyun.sbd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Component: 黑科技控制层
 * Description:
 * Date: 16/10/14
 *
 * @author yue.zhang
 */
@RestController
@RequestMapping(value = "/black-technologies")
public class BlackTechnologyController {

    private static final ConcurrentHashMap<String,AtomicInteger> COUNT_MAP = new ConcurrentHashMap<>();

    /**
     * 轮询负载均衡获取资源
     * @return
     */
    @RequestMapping(value = "/resources" , method = RequestMethod.GET)
    public String getResourceByRoundRobin(){
        String name = "resource";
        List<String> slaveResources = Arrays.asList("r1","r2","r3","r4");

        AtomicInteger count = COUNT_MAP.containsKey(name) ? COUNT_MAP.get(name) : new AtomicInteger(0);
        COUNT_MAP.putIfAbsent(name, count);
        count.compareAndSet(slaveResources.size(),0);
        return slaveResources.get(count.getAndIncrement() % slaveResources.size());
    }

}
