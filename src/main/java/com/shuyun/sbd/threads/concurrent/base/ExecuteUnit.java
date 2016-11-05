package com.shuyun.sbd.threads.concurrent.base;

/**
 * Component:
 * Description:
 * Date: 16/10/16
 *
 * @author yue.zhang
 */
public interface ExecuteUnit<I,O> {

    /**
     * 执行任务.
     *
     * @param input 输入待处理数据
     * @return 返回处理结果
     * @throws Exception 执行期异常
     */
    O execute(I input) throws Exception;
    
    <Y,S> Y execute2(S input);

}
