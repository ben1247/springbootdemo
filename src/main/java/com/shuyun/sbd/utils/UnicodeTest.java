package com.shuyun.sbd.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Component:
 * Description:
 * Date: 16/10/28
 *
 * @author yue.zhang
 */
public class UnicodeTest {

    public static void main(String [] args){
        System.out.println("default charset: " + Charset.defaultCharset());
        String str = "abc你好"; // string with UTF-8 charset

        byte[] bytes = str.getBytes(Charset.forName("UTF-8")); // convert to byte array with UTF-8 encode
        for(byte b : bytes){
            System.out.print(b + " ");
        }
        System.out.println();

        try {
            String str1 = new String(bytes,"UTF-8"); //to UTF-8 string
            String str2 = new String(bytes,"ISO-8859-1"); // to ISO-8859-1
            String str3 = new String(bytes,"GBK"); // to GBK string

            System.out.println(str1); // abc你好
            System.out.println(str2); // abc??????
            System.out.println(str3); // abc浣犲ソ

            System.out.println();

            byte [] bytes2 = str2.getBytes(Charset.forName("ISO-8859-1"));
            for(byte b : bytes2){
                System.out.print(b + " ");
            }
            System.out.println();

            String str22 = new String(bytes2,"UTF-8");
            System.out.println(str22); // abc你好

            System.out.println();
            byte [] bytes3 = str3.getBytes(Charset.forName("GBK"));
            for(byte b : bytes3){
                System.out.print(b + " ");
            }
            System.out.println();

            String str33 = new String(bytes3,"UTF-8");
            System.out.println(str33); //abc你好

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

    }

}
