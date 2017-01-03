package com.shuyun.sbd.utils.future.myFuture;

/**
 * Component:
 * Description:
 * Date: 16/12/29
 *
 * @author yue.zhang
 */
public class RealData implements Data {

    protected final String result;

    public RealData(String para){

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
        result = sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
