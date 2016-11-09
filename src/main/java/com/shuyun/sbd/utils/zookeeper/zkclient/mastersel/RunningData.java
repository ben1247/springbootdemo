package com.shuyun.sbd.utils.zookeeper.zkclient.mastersel;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 16/11/6
 *
 * @author yue.zhang
 */
public class RunningData implements Serializable {

    private static final long serialVersionUID = 7706480174034103258L;

    private Long cid;

    private String name;

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
