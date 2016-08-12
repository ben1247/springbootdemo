package com.shuyun.sbd.utils.netty.http.bean;

/**
 * Component:
 * Description:
 * Date: 16/8/11
 *
 * @author yue.zhang
 */
public class RequestParam {

    private String command;

    private Object parameter;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }
}
