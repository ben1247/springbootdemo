package com.shuyun.sbd.utils.jedis;

/**
 * Component:
 * Description:
 * Date: 16/3/31
 *
 * @author yue.zhang
 */
public class JedisClient {

    /**
     * 在不同的线程中使用相同的Jedis实例会发生奇怪的错误。但是创建太多的实现也不好因为这意味着会建立很多sokcet连接，
     * 也会导致奇怪的错误发生。单一Jedis实例不是线程安全的。为了避免这些问题，可以使用JedisPool,
     * JedisPool是一个线程安全的网络连接池。可以用JedisPool创建一些可靠Jedis实例，可以从池中拿到Jedis的实例。
     * 这种方式可以解决那些问题并且会实现高效的性能
     */

    public static void main(String[] args) {
        // ...when closing your application:
        JedisUtil.getInstance();
    }


}
