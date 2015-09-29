package com.shuyun.sbd.cache;

import com.google.common.base.Function;
import com.google.common.cache.*;
import com.google.common.collect.Maps;
import com.shuyun.sbd.domain.City;
import com.shuyun.sbd.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Component:
 * Description:
 * Date: 15/7/11
 *
 * @author yue.zhang
 */
@Component
public class CityCache {

    @Autowired
    private CityRepository cityRepository;

    private LoadingCache<Long,City> cache;

    public CityCache(){
        cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .removalListener(new RemovalListener<Long, City>() {
                    public void onRemoval(RemovalNotification<Long, City> n) {
                        if (n.wasEvicted()) {
                            System.out.println("key：" + n.getKey() + " value: " + n.getValue() + " 被系统自动移除了!");
                        } else {
                            System.out.println("key：" + n.getKey() + " value: " + n.getValue() + " 被人工手动移除了!");
                        }
                    }
                })
                .build(new CacheLoader<Long, City>() {
                    public City load(Long id) {
                        System.out.println("单个缓存未命中");
                        City city = cityRepository.findOne(id);
                        if (city != null) {
                            return city;
                        } else {
                            throw new NullPointerException("城市未找到");
                        }
                    }

                    public Map<Long, City> loadAll(List<Long> keys) {
                        System.out.println("批量缓存未命中");
                        List<City> cities = cityRepository.findAll();
                        // 将list转化成map
                        return Maps.uniqueIndex(cities, new Function<City, Long>() {
                            public Long apply(City city) {
                                return city.getId();
                            }
                        });
                    }
                });

    }

    public City put(City city){
        city = cityRepository.save(city);
        cache.put(city.getId(),city);
        return city;
    }

    public City get(Long id){
        return cache.getUnchecked(id);
    }

    public void delete(Long id){
        cityRepository.delete(id);
        cache.invalidate(id);
    }

    public void update(City city){
        cityRepository.save(city);
        cache.put(city.getId(),city);
    }

    public String getCityCacheStatus(){
        CacheStats stats = cache.stats();
        double hitRate = stats.hitRate();
        double missRate = stats.missRate();
        double loadExceptionRate = stats.loadExceptionRate();
        double averageLoadPenalty = stats.averageLoadPenalty();
        return "hitRate: " + hitRate + " ; missRate: " + missRate + " ;loadExceptionRate: " + loadExceptionRate + " averageLoadPenalty: " + averageLoadPenalty;
    }



}
