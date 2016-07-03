package com.shuyun.sbd.threads.concurrent.base;

/**
 * Component: 隋唐演义大戏舞台
 * Description:
 * Date: 16/6/21
 *
 * @author yue.zhang
 */
public class Stage extends Thread {

    public void run(){

        System.out.println("欢迎观看隋唐演义");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("大幕徐徐拉开");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("话说。。。。。。。。。。。。。。。");


        ArmyRunnable armyTaskOfSuiDynasty = new ArmyRunnable();
        ArmyRunnable armyTaskOfRevolt = new ArmyRunnable();

        Thread armyOfSuiDynasty = new Thread(armyTaskOfSuiDynasty,"隋军");
        Thread armyOfRevlot = new Thread(armyTaskOfRevolt,"农民起义军");

        // 启动线程，让军队开始作战
        armyOfSuiDynasty.start();
        armyOfRevlot.start();

        // 舞台线程休眠50毫秒，大家专心观看军队的厮杀
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("正当双方激战正酣，半路杀出了个程咬金");

//        Thread mrCheng = new KeyPersonThread();
//        mrCheng.setName("程咬金");
        KeyPersonRunnable keyPersonTaskOfMrCheng = new KeyPersonRunnable();
        Thread mrCheng = new Thread(keyPersonTaskOfMrCheng,"程咬金");

        System.out.println("程咬金的理想就是结束战争，使百姓安居乐业");


        // 停止军队作战
        // 停止线程的方法
        armyTaskOfSuiDynasty.keepRunning = false;
        armyTaskOfRevolt.keepRunning = false;
//        armyOfSuiDynasty.stop(); // 不要使用stop来停止线程
//        armyOfRevlot.stop(); // 不要使用stop来停止线程

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 历史大戏留给关键任务
        mrCheng.start();

        try {
            // 等待mrCheng执行完毕后主线程才往下执行
            mrCheng.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("战争结束了,人民过上了安居乐业的生活");
        System.out.println("谢谢观看隋唐演义，再见！");

    }

    public static void main(String [] args){
        new Stage().start();
    }

}
