package com.shuyun.sbd.utils.tools;

/**
 * Component:
 * Description:
 * Date: 16/3/24
 *
 * @author yue.zhang
 */
public class Constant {

    public static final int CUPS = Runtime.getRuntime().availableProcessors();//计算cpus个数
    public static final int THREADS = CUPS + 2;//最大(总)线程数
}
