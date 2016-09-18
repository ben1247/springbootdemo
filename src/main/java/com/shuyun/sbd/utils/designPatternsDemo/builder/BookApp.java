package com.shuyun.sbd.utils.designPatternsDemo.builder;

/**
 * Component:
 * Description:
 * Date: 16/9/4
 *
 * @author yue.zhang
 */
public class BookApp {

    public static void main(String [] args){
        Book b = new Book.Builder(1L).name("thinking java").price(100.00).build();
        System.out.println(b);
    }

}
