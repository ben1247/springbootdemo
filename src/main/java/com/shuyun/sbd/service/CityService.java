package com.shuyun.sbd.service;

import com.shuyun.sbd.cache.CityCache;
import com.shuyun.sbd.repository.CityRepository;
import com.shuyun.sbd.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Component:
 * Description:
 * Date: 15/7/5
 *
 * @author yue.zhang
 */
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityCache cityCache;

    public Iterable<City> queryCity(){
        return cityRepository.findAll();
    }

    public City addCity(City city){
        return cityCache.put(city);
    }

    public City findCity(Long id){
        return cityCache.get(id);
    }

    public void deleteCity(Long id){
        cityCache.delete(id);
    }

    public void updateCity(City city){
        cityCache.update(city);
    }

    public Iterable<City> queryCityByName(String name){
        return cityRepository.findByNameSql(name);
    }

    public Page<City> queryCity(String name , Pageable pageable){

//        Specification<City> search = new Specification<City>();
//        return cityRepository.findSearch(name, pageable);
        return cityRepository.findAll(pageable);

    }

    public String getCityCacheStatus(){
        return cityCache.getCityCacheStatus();
    }


}
