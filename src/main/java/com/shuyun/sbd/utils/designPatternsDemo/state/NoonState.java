package com.shuyun.sbd.utils.designPatternsDemo.state;

/**
 * Component:
 * Description:
 * Date: 15/8/8
 *
 * @author yue.zhang
 */
public class NoonState extends State{


    @Override
    public void writeProgram(Work w) {
        if(w.getHour() < 13){
            System.out.println(String.format("当前时间：%s点，午饭，午休",w.getHour()));
        }else{
            System.out.println("未完待续。。。。。。");
        }
    }
}
