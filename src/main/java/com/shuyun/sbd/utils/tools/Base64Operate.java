package com.shuyun.sbd.utils.tools;

import java.util.Base64;
import java.util.Date;

/**
 * Component:
 * Description:
 * Date: 15/8/7
 *
 * @author yue.zhang
 */
public class Base64Operate {

    public static String getBASE64(String str){
        if(str == null){
            return null;
        }
        return new String(Base64.getEncoder().encode(str.getBytes()));
    }

    public static String getFromBASE64(String str){
        if (str == null){
            return null;
        }
        return new String(Base64.getDecoder().decode(str.getBytes()));
    }

    public static void main(String [] args){
//        System.out.println(Base64Operate.getBASE64("api-developer:9c32a5a49645464fe05d7f3835cf51c4"));
//        System.out.println(Base64Operate.getFromBASE64(Base64Operate.getBASE64("api-developer:9c32a5a49645464fe05d7f3835cf51c4")));

        Date d = new Date(1441461440743L);
        System.out.println(d);
    }

}
