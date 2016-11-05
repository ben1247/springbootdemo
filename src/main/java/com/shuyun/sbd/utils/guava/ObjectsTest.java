package com.shuyun.sbd.utils.guava;

import com.google.common.base.Objects;

/**
 * Component:
 * Description:
 * Date: 16/10/23
 *
 * @author yue.zhang
 */
public class ObjectsTest {

    public static void main(String [] args){
        System.out.println(Objects.equal("a", "a")); // returns true
        System.out.println(Objects.equal(null, "a")); // returns false
        System.out.println(Objects.equal("a", null)); // returns false
        System.out.println(Objects.equal(null, null));
    }



}
