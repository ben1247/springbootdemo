package com.shuyun.sbd.utils.future.jdkFuture;

import java.util.concurrent.Callable;

/**
 * Component:
 * Description:
 * Date: 16/12/29
 *
 * @author yue.zhang
 */
public class RealData implements Callable<String> {

    private String para;

    public RealData(String para){
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        //RealData的构造可能很慢，需要用户等待很久，这里使用sleep模拟
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < 10 ; i++){
            sb.append(para);
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
