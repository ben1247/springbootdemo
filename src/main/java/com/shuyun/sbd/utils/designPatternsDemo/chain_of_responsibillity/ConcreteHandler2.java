package com.shuyun.sbd.utils.designPatternsDemo.chain_of_responsibillity;

/**
 * Component:
 * Description:
 * Date: 15/8/14
 *
 * @author yue.zhang
 */
public class ConcreteHandler2 extends Handler {

    @Override
    public void handleRequest(int request) {
        if(request >= 10 && request < 20){
            System.out.println(String.format("%s处理请求%s",this.getClass().getSimpleName(),request));
        }else if(successor != null){
            successor.handleRequest(request);
        }else{
            System.out.println(String.format("没人处理请求%s",request));
        }
    }
}
