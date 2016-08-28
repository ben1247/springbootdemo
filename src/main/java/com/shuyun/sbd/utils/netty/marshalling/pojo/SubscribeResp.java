package com.shuyun.sbd.utils.netty.marshalling.pojo;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 16/8/28
 *
 * @author yue.zhang
 */
public class SubscribeResp implements Serializable {

    private static final long serialVersionUID = 1L;

    private int subReqID;

    private int respCode;

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    @Override
    public String toString() {
        return "SubscribeResp{" +
                "desc='" + desc + '\'' +
                ", subReqID=" + subReqID +
                ", respCode=" + respCode +
                '}';
    }
}
