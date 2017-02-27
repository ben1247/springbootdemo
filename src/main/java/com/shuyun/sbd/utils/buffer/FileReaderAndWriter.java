package com.shuyun.sbd.utils.buffer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Component:
 * Description:
 * Date: 17/2/9
 *
 * @author yue.zhang
 */
public class FileReaderAndWriter {

    public static void main(String[] args) {

        long st = System.currentTimeMillis();

        FileReader fr = null;
        FileWriter fw = null;
        try{
            fr = new FileReader("file/1.txt");
            fw = new FileWriter("file/2.txt");

            char [] buf = new char[1024];

            int len = 0;
            while ((len = fr.read(buf)) != -1){
                fw.write(buf,0,len);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("copy finish , hosts: " + (System.currentTimeMillis() - st));
    }

}
