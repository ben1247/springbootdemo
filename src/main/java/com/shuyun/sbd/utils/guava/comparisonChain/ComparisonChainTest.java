package com.shuyun.sbd.utils.guava.comparisonChain;

/**
 * Component:
 * Description:
 * Date: 16/10/23
 *
 * @author yue.zhang
 */
public class ComparisonChainTest {

    public static void main(String [] args){

        ComparisonChainObj obj1 = new ComparisonChainObj(1,"a","aaa");
        ComparisonChainObj obj2 = new ComparisonChainObj(2,"a","aa");
        ComparisonChainObj obj3 = new ComparisonChainObj(1,"a","aaa");

        System.out.println(obj1.compareTo(obj2));
        System.out.println(obj1.compareTo(obj3));

    }

}
