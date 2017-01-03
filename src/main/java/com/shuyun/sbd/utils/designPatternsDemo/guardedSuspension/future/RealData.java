package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension.future;

/**
 * Component:
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class RealData implements Data {

    private final String content;

    public RealData(String para) {

        // 创建RealData比较耗时
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        this.content = para;
    }

    @Override
    public String getContent() {
        return content;
    }
}
