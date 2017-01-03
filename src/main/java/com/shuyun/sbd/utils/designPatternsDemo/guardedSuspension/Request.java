package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension;

/**
 * Component: 封装了请求的内容
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class Request {

    private String name;

    public Request(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                '}';
    }
}
