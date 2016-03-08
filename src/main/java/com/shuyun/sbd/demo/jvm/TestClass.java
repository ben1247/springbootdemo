package com.shuyun.sbd.demo.jvm;

public class TestClass {

    private int m;

    public int inc(){
        return m+1;
    }

    public int inc2(){
        int x;
        try{
            x = 1;
            throw new Exception();

        }catch (Exception e){
            x = 2;
//                  return x;

        } finally {
          x = 3;
        }

        return x;
    }

    public static void main(String [] args){
        TestClass t = new TestClass();
        System.out.println(t.inc2());
    }
}
