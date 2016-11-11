package com.shuyun.sbd.utils.zookeeper.zkclient.subscribe;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 16/11/11
 *
 * @author yue.zhang
 */
public class ServerConfig implements Serializable{

    private static final long serialVersionUID = -5981003417892875346L;

    private String dbUrl;
    private String dbUser;
    private String dbPwd;

    public String getDbPwd() {
        return dbPwd;
    }

    public void setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "dbPwd='" + dbPwd + '\'' +
                ", dbUrl='" + dbUrl + '\'' +
                ", dbUser='" + dbUser + '\'' +
                '}';
    }
}
