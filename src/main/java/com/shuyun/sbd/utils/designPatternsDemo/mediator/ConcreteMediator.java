package com.shuyun.sbd.utils.designPatternsDemo.mediator;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/8/15
 *
 * @author yue.zhang
 */
public class ConcreteMediator implements IMediator {

    private List<Colleague> colleagues;

    public ConcreteMediator(List<Colleague> _colleagues){
        this.colleagues = _colleagues;
        for(Colleague colleague : colleagues){
            colleague.setMediator(this);
        }
    }

    public void send(String _message, Colleague _colleague) {
        System.out.println(String.format("%s 发送消息 %s",_colleague.getName(),_message));
        for(Colleague colleague : colleagues){
            if(!colleague.getName().equals(_colleague.getName())){
                colleague.receive(_message);
            }
        }
    }

}
