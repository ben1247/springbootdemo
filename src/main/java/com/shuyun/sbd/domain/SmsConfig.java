package com.shuyun.sbd.domain;

/**
 * Component:
 * Description:
 * Date: 15/7/5
 *
 * @author yue.zhang
 */
public class SmsConfig {

    private String url;
    private String username;
    private String password;

    public SmsConfig(){}

    public SmsConfig(String _url,String _username ,String _password){
        this.url = _url;
        this.username = _username;
        this.password = _password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
