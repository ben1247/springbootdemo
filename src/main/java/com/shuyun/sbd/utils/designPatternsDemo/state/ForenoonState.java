package com.shuyun.sbd.utils.designPatternsDemo.state;

/**
 * Component:
 * Description:
 * Date: 15/8/8
 *
 * @author yue.zhang
 */
public class ForenoonState extends State {

    @Override
    public void writeProgram(Work w) {
        if(w.getHour() < 12){
            System.out.println(String.format("当前时间：%s点，上午工作，精神百倍",w.getHour()));
        }else{
            w.setCurrent(new NoonState());
            w.writeProgram();
        }
    }
}
