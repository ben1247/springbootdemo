package com.shuyun.sbd.utils.designPatternsDemo.mediator;

/**
 * Component:
 * Description:
 * Date: 15/8/15
 *
 * @author yue.zhang
 */
public abstract class Colleague {

    private String name;

    private IMediator mediator;

    public Colleague(String _name){
        this.name = _name;
    }

    public void send(String _message){
        this.getMediator().send(_message,this);
    }

    public abstract void receive(String _message);

    public void confirmReceive(String _message){
        System.out.println(String.format("%s确认收到消息［%s]",this.getName(),_message));
    }

    public String getName() {
        return name;
    }

    public void setMediator(IMediator _mediator) {
        this.mediator = _mediator;
    }

    public IMediator getMediator() {
        return mediator;
    }


}
