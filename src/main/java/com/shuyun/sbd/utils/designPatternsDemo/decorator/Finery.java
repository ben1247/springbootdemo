package com.shuyun.sbd.utils.designPatternsDemo.decorator;

/**
 * Component: 服饰类，继承了实体类，目的是继承实体类的方法并可扩展自己特有的属性和方法。
 * Description:
 * Date: 15/8/2
 *
 * @author yue.zhang
 */
public abstract class Finery extends Person{

    protected Person component;

    public void decorate(Person _component){
        this.component = _component;
    }

    @Override
    public void show() {
        if (component != null){
            component.show();
        }
    }

}
