package com.shuyun.sbd.demo.jvm.script;

/**
 * Component: 为了多次载入执行类而加入的加载器
 * Description:
 * 把defineClass方法开放出来只有外部显式调用的时候才会使用到loadByte方法
 * 由虚拟机调用时，仍然按照原有的双亲委派规则使用loadClass方法进行类加载
 * Date: 16/3/9
 * @author yue.zhang
 */
public class HotSwapClassLoader extends ClassLoader{

    public HotSwapClassLoader(){
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte [] classByte){
        return defineClass(null,classByte,0,classByte.length);
    }

}
