package com.shuyun.sbd.utils.bio.forgeNio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Component: 伪异步i/o
 * Description:
 * Date: 16/9/18
 *
 * @author yue.zhang
 */
public class TimeServer {

    public static void main(String [] args) throws IOException {
        int port = 8999;
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                // 采用默认值
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port: " + port);
            Socket socket = null;
            // 创建I/O任务线程池
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50,1000);
            while (true){
                socket = server.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }

        }finally {
            if(server != null){
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }

}
