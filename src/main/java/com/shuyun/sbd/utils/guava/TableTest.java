package com.shuyun.sbd.utils.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Collection;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 16/10/25
 *
 * @author yue.zhang
 */
public class TableTest {

    public static void main(String [] args){

        Table<Integer,Boolean,String> myTable = HashBasedTable.create();
        myTable.put(1,true,"a");
        myTable.put(1,false,"b");
        myTable.put(2,true,"aa");
        myTable.put(2,false,"bb");
        myTable.put(2,true,"cc");
        myTable.put(3,false,"aaa");
        myTable.put(3,true,"bbb");
        myTable.put(3, false, "ccc");

        Map<Boolean,String> row1 = myTable.row(1);
        System.out.println(row1);

        Map<Integer,String> columnTrue = myTable.column(true);
        System.out.println(columnTrue);

        Collection<String> values = myTable.values();
        System.out.println(values);
    }

}
