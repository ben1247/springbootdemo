package com.shuyun.sbd.demo.jvm;

/**
 * Component: 此代码演示了两点：
 * 1. 对象可以在被GC时自我拯救
 * 2. 这种自救的机会只有一次，因为一个对象的finalize()方法最多只会被系统自动调用一次
 *
 * finalize 是对c++程序员的一种妥协策略，使用try-finally或者其他方式都可以做的更好，所以不建议使用
 *
 * Description:
 * Date: 16/2/16
 *
 * @author yue.zhang
 */
public class FinalizeExcapeGC {

    public static FinalizeExcapeGC SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("yes, i am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeExcapeGC.SAVE_HOOK = this;
    }

    public static void main(String[]args)throws Throwable {
        SAVE_HOOK = new FinalizeExcapeGC();
        // 对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("no, i am dead :(");
        }

        // 下面这段代码和上面完全相同，但是这次自救却失败了
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("no, i am dead :(");
        }
    }
}
