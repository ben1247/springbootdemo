package com.shuyun.sbd.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
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
@ConfigurationProperties(locations = "classpath:mail.properties" , prefix = "mail")
public class MailController {
    private String host;
    private int port;
    private String from;
    private String username;
    private String password;
    private Smtp smtp;


    @RequestMapping("/mail/config")
    @ResponseBody
    public String mailConfig(){
        StringBuilder sb = new StringBuilder();
        sb.append("host=" + host);
        sb.append(";port=" + port);
        sb.append(";from=" + from);
        sb.append(";username=" + username);
        sb.append(";password=" + password);
        sb.append(";auth=" + smtp.auth);
        sb.append(";starttlsEnable=" + smtp.starttlsEnable);

        return sb.toString();
    }

    public static class Smtp {

        private boolean auth;
        private boolean starttlsEnable;


        public boolean isAuth() {
            return auth;
        }

        public void setAuth(boolean auth) {
            this.auth = auth;
        }

        public boolean isStarttlsEnable() {
            return starttlsEnable;
        }

        public void setStarttlsEnable(boolean starttlsEnable) {
            this.starttlsEnable = starttlsEnable;
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Smtp getSmtp() {
        return smtp;
    }

    public void setSmtp(Smtp smtp) {
        this.smtp = smtp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
