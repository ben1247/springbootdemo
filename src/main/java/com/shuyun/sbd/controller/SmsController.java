package com.shuyun.sbd.controller;

import com.shuyun.sbd.domain.SmsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Component:
 * Description:
 * Date: 15/7/5
 *
 * @author yue.zhang
 */
@Controller
public class SmsController {

    @Value("${sms.gd.url}")
    private String url;
    @Value("${sms.gd.username}")
    private String username;
    @Value("${sms.gd.password}")
    private String password;


    @RequestMapping("/sms/config")
    @ResponseBody
    public SmsConfig getSmsConfig(){
        return new SmsConfig(url,username,password);
    }

}
