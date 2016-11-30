package com.shuyun.sbd.utils.queue.priority;

/**
 * Component:
 * Description:
 * Date: 16/11/20
 *
 * @author yue.zhang
 */
public class Customer {

    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}