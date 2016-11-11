package com.shuyun.sbd.utils.zookeeper.zkclient.subscribe;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 16/11/11
 *
 * @author yue.zhang
 */
public class ServerData implements Serializable{

    private static final long serialVersionUID = 2381064632678163774L;

    private String address;
    private Integer id;
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServerData{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
