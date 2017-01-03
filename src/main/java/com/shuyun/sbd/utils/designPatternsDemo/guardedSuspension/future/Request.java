package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension.future;

/**
 * Component:
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class Request {

    private String name;

    // 请求的返回值
    private Data response;

    public Request(String name) {
        this.name = name;
    }

    public synchronized Data getResponse(){
        return response;
    }

    public synchronized void setResponse(Data response) {
        this.response = response;
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
