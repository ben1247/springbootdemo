package com.shuyun.sbd.utils.zookeeper.curator.discovery;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Component: InstanceDetails定义了服务实例的基本信息,实际中可能会定义更详细的信息。
 * Description:
 *  In a real application, the Service payload will most likely be more detailed
 *  than this. But, this gives a good example.
 * Date: 15/11/29
 *
 * @author yue.zhang
 */
@JsonRootName("details")
public class InstanceDetails {

    private String description;

    public InstanceDetails(){
        this("");
    }

    public InstanceDetails(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
