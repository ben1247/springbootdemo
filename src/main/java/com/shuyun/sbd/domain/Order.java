package com.shuyun.sbd.domain;

/**
 * Component:
 * Description:
 * Date: 15/7/5
 *
 * @author yue.zhang
 */
public class Order {
    private String tid;
    private String oid;
    private String title;

    public Order(String _tid, String _oid, String _title){
        this.tid = _tid;
        this.oid = _oid;
        this.title = _title;
    }


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
