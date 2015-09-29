package com.shuyun.sbd.utils.nio;

/**
 * Component:
 * Description:
 * Date: 15/9/3
 *
 * @author yue.zhang
 */
public interface CompletionHandler2<V,A> {

    void completed(V result, A attachement);

}
