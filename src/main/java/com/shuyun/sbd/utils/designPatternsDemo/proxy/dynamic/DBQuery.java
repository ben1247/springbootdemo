package com.shuyun.sbd.utils.designPatternsDemo.proxy.dynamic;

/**
 * Component:
 * Description:
 * Date: 16/12/19
 *
 * @author yue.zhang
 */
public class DBQuery implements IDBQuery {

    public DBQuery(){

    }

    @Override
    public String request() {
        return "request string";
    }

    @Override
    public String response() {
        return "response string";
    }
}
