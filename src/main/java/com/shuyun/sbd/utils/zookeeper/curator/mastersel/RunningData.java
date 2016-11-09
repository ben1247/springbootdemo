package com.shuyun.sbd.utils.zookeeper.curator.mastersel;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 16/11/8
 *
 * @author yue.zhang
 */
public class RunningData implements Serializable {

    private static final long serialVersionUID = -3304712529711565692L;

    private Long cid;

    private String name;

    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
