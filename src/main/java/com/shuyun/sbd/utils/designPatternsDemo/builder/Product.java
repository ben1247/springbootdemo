package com.shuyun.sbd.utils.designPatternsDemo.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/8/5
 *
 * @author yue.zhang
 */
public class Product {
    private List<String> parts = new ArrayList<String>();

    public void add(String part){
        parts.add(part);
    }

    public void show(){
        System.out.println("\n产品 创建----");
        for(String part : parts){
            System.out.println(part);
        }
    }
}
