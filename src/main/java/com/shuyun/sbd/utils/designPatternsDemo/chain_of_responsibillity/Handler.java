package com.shuyun.sbd.utils.designPatternsDemo.chain_of_responsibillity;

/**
 * Component:
 * Description:
 * Date: 15/8/14
 *
 * @author yue.zhang
 */
public abstract class Handler {

    protected Handler successor;

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(int request);
}
