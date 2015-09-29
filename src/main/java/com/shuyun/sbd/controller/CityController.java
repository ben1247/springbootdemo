package com.shuyun.sbd.controller;

import com.shuyun.sbd.domain.City;
import com.shuyun.sbd.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Iterator;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/7/5
 *
 * @author yue.zhang
 */
@RestController
@RequestMapping(value = "/cities")
public class CityController {

    @Autowired
    private CityService cityService;


    @RequestMapping(method = RequestMethod.GET)
    public List<City> queryCity(@RequestParam String name){
        Pageable page = new PageRequest(1,5);
        Page<City> cities =  cityService.queryCity(name,page);
        return cities.getContent();
    }

//    @RequestMapping(value = "/search" , method = RequestMethod.GET)
//    public List<City> queryCity2(@RequestParam String name){
//        return cityService.queryCity2(name);
//    }

    @RequestMapping(method = RequestMethod.POST)
    public City addCity(@RequestBody City city){
        return cityService.addCity(city);
    }

    @RequestMapping(value = "/{cityId}" , method = RequestMethod.GET)
    public City getCity(@PathVariable Long cityId){
        return cityService.findCity(cityId);
    }

    @RequestMapping(value = "/{cityId}" , method = RequestMethod.DELETE)
    public void deleteCity(@PathVariable Long cityId){
        cityService.deleteCity(cityId);
    }

    @RequestMapping(value = "/{cityId}" , method = RequestMethod.PUT)
    public void updateCity(@RequestBody City city){
        cityService.updateCity(city);
    }


    @RequestMapping(value = "/cache/status" , method = RequestMethod.GET)
    public String getCityCacheStatus(){
        return cityService.getCityCacheStatus();
    }
}
