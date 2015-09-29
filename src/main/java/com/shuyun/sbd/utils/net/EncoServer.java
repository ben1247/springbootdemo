package com.shuyun.sbd.utils.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Component:
 * Description:
 * Date: 15/8/23
 *
 * @author yue.zhang
 */
public class EncoServer {

    public static void main(String [] args) throws IOException{

        try(ServerSocket ss = new ServerSocket(8189)){

            try(Socket incoming = ss.accept()){
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                try(Scanner in = new Scanner(inStream)){

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
            }
        }

    }

}
