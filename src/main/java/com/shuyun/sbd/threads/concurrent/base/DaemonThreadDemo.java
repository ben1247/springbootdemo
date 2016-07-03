package com.shuyun.sbd.threads.concurrent.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Component:
 * Description:
 * Date: 16/6/26
 *
 * @author yue.zhang
 */
public class DaemonThreadDemo {

    public static void main(String [] args){
        System.out.println("进入主线程" + Thread.currentThread().getName());

        DaemonRunnable daemonRunnable = new DaemonRunnable();
        Thread thread = new Thread(daemonRunnable);
        thread.setDaemon(true);
        thread.start();

        Scanner sc = new Scanner(System.in);
        sc.next();

        System.out.println("退出主线程" + Thread.currentThread().getName());
    }

}

class DaemonRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("进入守护线程" + Thread.currentThread().getName());
        try {
            writeToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("退出守护线程" + Thread.currentThread().getName());
    }

    private void writeToFile() throws Exception{
        File filename = new File("/Users/yuezhang/work/workspaces/intellij_workspaces/springbootdemo/daemon.txt");
        OutputStream os = new FileOutputStream(filename,true);
        int count = 0;
        while (count < 999){
            os.write(("\r\nword" + count).getBytes());
            System.out.println("守护线程" + Thread.currentThread().getName() + " 向文件中写入了word" + count++);
            Thread.sleep(1000);
        }
    }
}

