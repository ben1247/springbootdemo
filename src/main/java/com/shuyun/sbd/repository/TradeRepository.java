package com.shuyun.sbd.repository;

import com.shuyun.sbd.domain.Order;
import com.shuyun.sbd.domain.Trade;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/7/4
 *
 * @author yue.zhang
 */
@Repository
public class TradeRepository {

    public Trade getTrade(String tid){
        return new Trade(tid,"pay",10.0);
    }

    public List<Order> getOrders(String tid){
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order(tid,tid+1,"子订单1"));
        orders.add(new Order(tid,tid+2,"子订单2"));
        orders.add(new Order(tid,tid+3,"子订单3"));
        return orders;
    }

}
