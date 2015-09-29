package com.shuyun.sbd.controller;

import com.shuyun.sbd.domain.Order;
import com.shuyun.sbd.domain.Trade;
import com.shuyun.sbd.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/7/3
 *
 * @author yue.zhang
 */
@Controller
@RequestMapping(value = "/trades")
public class TradeController {

    @Resource
    private TradeService tradeService;

    @RequestMapping(value = "/{tid}", method = RequestMethod.GET)
    @ResponseBody
    public Trade getTrade(@PathVariable String tid){
        return tradeService.getTrade(tid);
    }

    @RequestMapping(value = "/{tid}/orders", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> getOrders(@PathVariable String tid){
        return tradeService.getOrders(tid);
    }

}
