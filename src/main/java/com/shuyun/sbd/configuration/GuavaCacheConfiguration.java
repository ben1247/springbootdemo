package com.shuyun.sbd.configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.shuyun.sbd.domain.City;

import java.util.concurrent.ExecutionException;

/**
 * Component:
 * Description:
 * Date: 15/7/11
 *
 * @author yue.zhang
 */
public class GuavaCacheConfiguration {

    public static void main(String [] args){
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            public String load(String key) {
                return key.toUpperCase();
            }
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);
        System.out.println(cache.size());
        System.out.println(cache.getUnchecked("simple test"));
        System.out.println(cache.size());
        System.out.println(cache.getUnchecked("simple test"));

        cache.put("a", "aaaa");


        try {
            System.out.println(cache.get("a"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        try {
//            String value = loader.load("hello world");
//            System.out.println(value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }
}
