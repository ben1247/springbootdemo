package com.shuyun.sbd.utils.designPatternsDemo.mediator;

/**
 * Component:
 * Description:
 * Date: 15/8/15
 *
 * @author yue.zhang
 */
public interface IMediator {

    void send(String message,Colleague colleague);

}
