package com.shuyun.sbd.utils.proxy;

/**
 * 真实角色-java程序员
 * Created by yuezhang on 18/3/24.
 */
public class JavaCoder implements ICoder {

    private String name;

    public JavaCoder(String name) {
        this.name = name;
    }

    @Override
    public void implDemands(String demandName) {
        System.out.println(name + " impl demands " + demandName + " in java");
    }
}
