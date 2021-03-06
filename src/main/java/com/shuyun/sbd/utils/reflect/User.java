package com.shuyun.sbd.utils.reflect;

/**
 * Component:
 * Description:
 * Date: 16/12/8
 *
 * @author yue.zhang
 */
public class User {

    private int age;
    private String name;

    public User() {
        super();
    }

    public User(String name) {
        super();
        this.name = name;
    }

    public User(int age, String name) {
        super();
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [age=" + age + ", name=" + name + "]";
    }

    public void test(int a , String b){
        System.out.println("doing test a: " + a + "  b: " + b);
    }

}
