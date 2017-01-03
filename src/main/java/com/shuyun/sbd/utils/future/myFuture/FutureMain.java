package com.shuyun.sbd.utils.future.myFuture;

/**
 * Component:
 * Description:
 * Date: 16/12/29
 *
 * @author yue.zhang
 */
public class FutureMain {

    public static void main(String [] args){
        Client client = new Client();

        Data  data = client.request("name");
        System.out.println("请求完毕");
        try {
            // 这里可以用一个sleep代替对其他业务逻辑的处理
            // 在处理这些业务逻辑的过程中，RealData被创建，从而充分利用了等待时间
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // 使用真实的数据
        System.out.println("数据=" + data.getResult());
    }

}
