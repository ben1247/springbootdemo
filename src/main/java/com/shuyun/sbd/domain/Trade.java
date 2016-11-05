package com.shuyun.sbd.domain;

/**
 * Component:
 * Description:
 * Date: 15/7/3
 *
 * @author yue.zhang
 */
public class Trade {
    private String tid;
    private String status;
    private Double price;

    public Trade(String _tid , String _status , Double _price){
        this.tid = _tid;
        this.status = _status;
        this.price = _price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "price=" + price +
                ", tid='" + tid + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
