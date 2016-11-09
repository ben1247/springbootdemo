package com.shuyun.sbd.utils.zookeeper.curator.mastersel;

/**
 * Component: 触发一下mainstem发生切换
 * Description:
 * Date: 16/11/8
 *
 * @author yue.zhang
 */
public interface RunningListener {

    /**
     * 启动时回调做点事情
     * @param context
     */
    void processStart(Object context);

    /**
     * 关闭时回调做点事情
     * @param context
     */
    void processStop(Object context);

    /**
     * 触发现在轮到自己做为active，需要载入上一个active的上下文数据
     * @param context
     */
    void processActiveEnter(Object context);

    /**
     * 触发一下当前active模式失败
     */
    void processActiveExit(Object context);
}
