package com.shuyun.sbd.service;

import com.shuyun.sbd.repository.TradeRepository;
import com.shuyun.sbd.domain.Order;
import com.shuyun.sbd.domain.Trade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/7/4
 *
 * @author yue.zhang
 */
@Service
public class TradeService {

    @Resource
    private TradeRepository tradeDao;

    public Trade getTrade(String tid){
        return tradeDao.getTrade(tid);
    }

    public List<Order> getOrders(String tid){
        return tradeDao.getOrders(tid);
    }

}
