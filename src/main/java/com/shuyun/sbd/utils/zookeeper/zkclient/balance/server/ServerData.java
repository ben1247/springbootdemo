package com.shuyun.sbd.utils.zookeeper.zkclient.balance.server;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class ServerData implements Serializable , Comparable<ServerData>{

    private static final long serialVersionUID = 6077463459722608399L;

    private Integer balance; // 负载

    private String host;

    private Integer port;

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServerData{" +
                "balance=" + balance +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

    @Override
    public int compareTo(ServerData o) {
        return this.getBalance().compareTo(o.getBalance()) ;
    }
}
