package com.shuyun.sbd.controller;

import com.shuyun.sbd.domain.Trade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Component:
 * Description:
 * Date: 15/7/3
 *
 * @author yue.zhang
 */
@Controller
public class SampleController {

    @RequestMapping("/home")
    @ResponseBody
    public String home(){
        return "Hello World!";
    }

}
