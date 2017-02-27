package com.shuyun.sbd.utils.buffer;

import java.io.*;

/**
 * Component:
 * Description:
 * Date: 17/2/9
 *
 * @author yue.zhang
 */
public class BufferedReaderAndWriter {

    public static void main(String[] args) {

        long st = System.currentTimeMillis();

        BufferedReader bufr = null;
        BufferedWriter bufw = null;
        try {
            bufr = new BufferedReader(new FileReader("file/1.txt"));
            bufw = new BufferedWriter(new FileWriter("file/2.txt"));

            String line = null;

            while ((line = bufr.readLine()) != null){
                bufw.write(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bufr != null){
                try {
                    bufr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufw != null){
                try {
                    bufw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        System.out.println("copy finish , hosts: " + (System.currentTimeMillis() - st));
    }

}
