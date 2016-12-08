package com.shuyun.sbd.utils.reflect;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.ArrayList;

/**
 * Component:
 * Description:
 * Date: 16/12/8
 *
 * @author yue.zhang
 */
public class ReflectDemoTest {

    /**
     * 通过一个对象获得完整的包名和类名
     */
    @Test
    public void testObjToClassName(){
        ReflectDemo ReflectDemo = new ReflectDemo();
        System.out.println(ReflectDemo.getClass().getName());
    }

    /**
     * 实例化Class类对象
     */
    @Test
    public void testClassForName() throws ClassNotFoundException {
        //一般采用这种形式
        Class<?> class1 = Class.forName("com.shuyun.sbd.utils.reflect.ReflectDemo");
        Class<?> class2 = new ReflectDemo().getClass();
        Class<?> class3 = ReflectDemo.class;

        System.out.println("类1名称   " + class1.getName());
        System.out.println("类2名称   " + class2.getName());
        System.out.println("类3名称   " + class3.getName());
    }

    /**
     * 获取一个对象的父类与实现的接口
     */
    @Test
    public void testSuperClassAndInterface() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.shuyun.sbd.utils.reflect.ReflectDemo");
        // 取得父类
        Class<?> parentClass = clazz.getSuperclass();
        System.out.println("clazz的父类为：" + parentClass.getName()); // clazz的父类为：java.lang.Object
        // 获取所有接口
        Class<?>[] intes = clazz.getInterfaces();
        // clazz实现的接口有：
        // 1：java.io.Serializable
        System.out.println("clazz的实现接口有：");
        for(int i = 0 ; i < intes.length; i++){
            System.out.println((i + 1) + "：" + intes[i].getName());
        }
    }

    /**
     * 通过反射机制实例化一个类的对象
     */
    @Test
    public void testCreateObj() throws Exception {
        Class<?> userClass = Class.forName("com.shuyun.sbd.utils.reflect.User");
        // 第一种方法，实例化默认构造方法，调用set赋值
        User user = (User)userClass.newInstance();
        user.setAge(20);
        user.setName("Ben");
        System.out.println(user);

        // 第二种方法，取得全部的构造函数 使用构造函数赋值
        Constructor<?>[] cons = userClass.getConstructors();
        // 查看每个构造方法需要的参数
        for(int i = 0 ; i < cons.length; i++){
            Class<?> [] classes = cons[i].getParameterTypes();
            System.out.print("cons[" + i + "](");
            for(int j = 0 ; j < classes.length; j++){
                if(j == classes.length - 1)
                    System.out.print(classes[j].getName());
                else
                    System.out.print(classes[j].getName() + ",");
            }
            System.out.println(")");
        }

        user = (User)cons[0].newInstance(20,"Ben");
        System.out.println(user);

        user = (User)cons[1].newInstance("Ben");
        System.out.println(user);
    }

    /**
     * 获取某个类的全部属性
     */
    @Test
    public void testFields() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.shuyun.sbd.utils.reflect.ReflectDemo");
        System.out.println("===============本类属性===============");
        // 取得本类的全部属性
        Field [] fields = clazz.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            // 权限修饰符
            int mo = fields[i].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = fields[i].getType();
            System.out.println(priv + " " + type.getName() + " " + fields[i].getName());
        }
        System.out.println("==========实现的接口或者父类的属性==========");
        // 取得实现的接口或者父类的属性
        fields = clazz.getFields();
        for(int i = 0; i < fields.length; i++){
            // 权限修饰符
            int mo = fields[i].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = fields[i].getType();
            System.out.println(priv + " " + type.getName() + " " + fields[i].getName());
        }
    }

    /**
     * 获取某个类的全部方法
     */
    @Test
    public void testMethods() throws ClassNotFoundException {
        Class<?> clazz =  Class.forName("com.shuyun.sbd.utils.reflect.ReflectDemo");
        Method[] methods = clazz.getMethods();
        for(int i = 0 ; i < methods.length; i++){

            // 方法
            Method method = methods[i];
            Class<?> returnType = method.getReturnType();
            int mo = method.getModifiers();
            System.out.print(Modifier.toString(mo) + " "); // 权限修饰符
            System.out.print(returnType.getName() + " "); // 返回类型
            System.out.print(method.getName() + " "); // 方法名
            System.out.print("(");

            // 方法参数
            Class<?> [] paras = method.getParameterTypes();
            for(int j = 0 ; j < paras.length; j++){
                System.out.print(paras[j].getName() + " " + "arg" + j);
                if(j < paras.length - 1){
                    System.out.print(",");
                }
            }
            System.out.print(")");

            // 方法抛出的异常
            Class<?> [] exces = method.getExceptionTypes();
            if(exces.length > 0){
                System.out.print(" throws ");
                for(int j = 0 ; j < exces.length; j++){
                    System.out.print(exces[j].getName() + " ");
                    if(j < exces.length - 1){
                        System.out.print(",");
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * 通过反射机制调用某个类的方法
     * @throws Exception
     */
    @Test
    public void testCallMethod() throws Exception{
        Class<?> clazz = Class.forName("com.shuyun.sbd.utils.reflect.ReflectDemo");

        Method method = clazz.getMethod("reflect1");
        method.invoke(clazz.newInstance());

        method = clazz.getMethod("reflect2",int.class,String.class);
        method.invoke(clazz.newInstance(),20,"ben");
    }

    /**
     * 通过反射机制操作某个类的属性
     */
    @Test
    public void testSetField() throws Exception {
        Class<?> clazz = Class.forName("com.shuyun.sbd.utils.reflect.ReflectDemo");
        Object obj = clazz.newInstance();
        // 可以直接对private的属性赋值
        Field field = clazz.getDeclaredField("property");
        field.setAccessible(true);
        field.set(obj, "Java反射机制");
        System.out.println(field.get(obj));
    }

    /**
     * 反射机制的动态代理
     *
     * 在java中有三种类类加载器。
     * 1）Bootstrap ClassLoader 此加载器采用c++编写，一般开发中很少见。
     * 2）Extension ClassLoader 用来进行扩展类的加载，一般对应的是jrelibext目录中的类
     * 3）AppClassLoader 加载classpath指定的类，是最常用的加载器。同时也是java中默认的加载器。
     * 如果想要完成动态代理，首先需要定义一个InvocationHandler接口的子类，已完成代理的具体操作。
     * @author xsoftlab.net
     */
    @Test
    public void testReflectDynamicProxy(){
        MyInvocationHandler demo = new MyInvocationHandler();
        Subject sub = (Subject) demo.bind(new RealSubject());
        String info = sub.say("ben",20);
        System.out.println(info);
    }

    /**
     * 在泛型为Integer的ArrayList中存放一个String类型的对象。
     */
    @Test
    public void testListToReflect() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        Method method = list.getClass().getMethod("add", Object.class);
        method.invoke(list,"Java反射机制实例");
        System.out.println(list.get(0));
    }

    /**
     * 通过反射取得并修改数组的信息
     * 关键是使用了 java.lang.reflect.Array
     * @throws Exception
     */
    @Test
    public void testArrayToReflect() throws Exception{
        int [] array = {1,2,3,4,5};
        Class<?> arrayType = array.getClass().getComponentType(); // 数组元素的类型
        System.out.println("数组类型： " + arrayType.getName());
        System.out.println("数组长度：" + Array.getLength(array));
        System.out.println("数组的第一个元素：" + Array.get(array,0));
        Array.set(array, 0, 100);
        System.out.println("修改之后数组的第一个元素为：" + Array.get(array,0));
    }

    /**
     * 通过反射机制修改数组的大小
     * @throws Exception
     */
    @Test
    public void testUpdateArrayToReflect() throws Exception{
        int [] array = {1,2,3,4,5,6,7,8,9};
        int [] newArray = (int []) ReflectDemo.arrayInc(array, 15);
        ReflectDemo.print(newArray);

        String [] array1 = {"a","b","c"};
        String [] newArray1 = (String [])ReflectDemo.arrayInc(array1,8);
        ReflectDemo.print(newArray1);
    }

    /**
     * 将反射机制应用于工厂模式
     *
     * 对于普通的工厂模式当我们在添加一个子类的时候，就需要对应的修改工厂类。 当我们添加很多的子类的时候，会很麻烦。
     * Java 工厂模式可以参考
     * http://baike.xsoftlab.net/view/java-factory-pattern
     *
     * 现在我们利用反射机制实现工厂模式，可以在不修改工厂类的情况下添加任意多个子类。
     *
     * 但是有一点仍然很麻烦，就是需要知道完整的包名和类名，这里可以使用properties配置文件来完成。
     *
     * java 读取 properties 配置文件 的方法可以参考
     * http://baike.xsoftlab.net/view/java-read-the-properties-configuration-file
     *
     * @author xsoftlab.net
     */
    @Test
    public void testFactoryToReflect() throws Exception{
        Fruit f = Factory.getInstance("com.shuyun.sbd.utils.reflect.Apple");
        if(f != null){
            f.eat();
        }
    }

}
