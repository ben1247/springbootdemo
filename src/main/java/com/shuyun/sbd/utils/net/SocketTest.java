package com.shuyun.sbd.utils.net;

import javafx.stage.Screen;
import org.jboss.netty.channel.local.LocalAddress;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Component:
 * Description:
 * Date: 15/8/23
 *
 * @author yue.zhang
 */
public class SocketTest {

    private static void createSocket1() {
        try(Socket s = new Socket("time-A.timefreq.bldrdoc.gov",13)){
            s.setSoTimeout(10000); // 超时时间
            InputStream in = s.getInputStream();
            Scanner scanner = new Scanner(in);
            while(scanner.hasNext()){
                String line = scanner.next();
                System.out.println(line);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void createSocket2(){
        try(Socket s = new Socket()){

            s.connect(new InetSocketAddress("time-A.timefreq.bldrdoc.gov",13),10000);
            InputStream in = s.getInputStream();
            Scanner scanner = new Scanner(in);
            while(scanner.hasNext()){
                String line = scanner.next();
                System.out.println(line);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void getInetAddress(){
        try {
            InetAddress inetAddress = InetAddress.getByName("time-A.timefreq.bldrdoc.gov");
            System.out.println(inetAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void getLocalAddress(){
        try {
            InetAddress localAddress = InetAddress.getLocalHost();
            System.out.println(localAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
//        createSocket1();
//        createSocket2();
//        getInetAddress();
        getLocalAddress();
    }

}
