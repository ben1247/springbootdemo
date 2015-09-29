package com.shuyun.sbd.utils.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Component:
 * Description:
 * Date: 15/8/23
 *
 * @author yue.zhang
 */
public class ThreadEchoHandler implements Runnable {

    private Socket incoming;

    public ThreadEchoHandler(Socket _incoming){
        this.incoming = _incoming;
    }

    @Override
    public void run() {
        try {
            try{
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream,true /* autoFlush */);
                out.println("Hello ! Enter BYE to exit.");

                // echo client input
                boolean done = false;
                while(!done && in.hasNextLine()){
                    String line = in.nextLine();
                    out.println(String.format("Echo: %s",line));
                    if(line.trim().equals("BYE")){
                        done = true;
                    }
                }
            }
            finally {
                incoming.close();
//                System.out.println("想关闭？门都没有！！！");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
