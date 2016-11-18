package com.shuyun.sbd.utils.zookeeper.zkclient.queue;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 16/11/17
 *
 * @author yue.zhang
 */
public class User implements Serializable {

    private static final long serialVersionUID = 310828944378922781L;

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
