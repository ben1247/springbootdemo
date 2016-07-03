package com.shuyun.sbd.annotation.demo1;

/**
 * Component: 苹果类
 * Description:
 * Date: 16/4/11
 *
 * @author yue.zhang
 */
public class Apple {

    @FruitName("Apple")
    private String appleName;

    @FruitColor(FruitColor.Color.RED)
    private String appleColor;

    @FruitProvider(id=1,name = "上海红富士集团",address = "上海市黄浦区复兴中路78弄")
    private String appleProvider;

    public String getAppleColor() {
        return appleColor;
    }

    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }

    public String getAppleName() {
        return appleName;
    }

    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }

    public String getAppleProvider() {
        return appleProvider;
    }

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }

    public void displayName(){
        System.out.println("水果的名字是：苹果");
    }

}
