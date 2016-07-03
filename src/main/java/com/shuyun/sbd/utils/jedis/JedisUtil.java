package com.shuyun.sbd.utils.jedis;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Component: Redis工具类,用于获取RedisPool.
 * Description:
 * 参考官网说明如下：
 * You shouldn't use the same instance from different threads because you'll have strange errors.
 * And sometimes creating lots of Jedis instances is not good enough because it means lots of sockets and connections,
 * which leads to strange errors as well. A single Jedis instance is not threadsafe!
 * To avoid these problems, you should use JedisPool, which is a threadsafe pool of network connections.
 * This way you can overcome those strange errors and achieve great performance.
 * To use it, init a pool:
 *  JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
 *  You can store the pool somewhere statically, it is thread-safe.
 *  JedisPoolConfig includes a number of helpful Redis-specific connection pooling defaults.
 *  For example, Jedis with JedisPoolConfig will close a connection after 300 seconds if it has not been returned.
 * Date: 16/3/31
 *
 * @author yue.zhang
 */
public class JedisUtil {

    protected Logger LOOGER = LoggerFactory.getLogger(JedisUtil.class);

    /**
     * 私有构造器
     */
    private JedisUtil(){

    }

    private static Map<String,JedisPool> maps = new HashMap<>();


    /**
     * 获取连接池
     * @param ip
     * @param port
     * @return
     */
    private JedisPool getPool(String ip , int port){
        String key = ip + ":" + port;
        JedisPool pool = null;
        if(!maps.containsKey(key)){
            JedisPoolConfig config = new JedisPoolConfig();
            //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
            config.setBlockWhenExhausted(true);
            //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
            config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
            //是否启用pool的jmx管理功能, 默认true
            config.setJmxEnabled(true);
            config.setJmxNamePrefix("pool");
            //是否启用后进先出, 默认true
            config.setLifo(true);
            //最大空闲连接数, 默认8个
            config.setMaxIdle(8);
            //最大连接数, 默认8个
            config.setMaxTotal(8);
            //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
            config.setMinEvictableIdleTimeMillis(1800000);
            //最小空闲连接数, 默认0
            config.setMinIdle(0);
            //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
            config.setNumTestsPerEvictionRun(3);
            //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
            config.setSoftMinEvictableIdleTimeMillis(1800000);
            //在获取连接的时候检查有效性, 默认false
            config.setTestOnBorrow(true);
            //在空闲时检查有效性, 默认false
            config.setTestWhileIdle(false);
            //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
            config.setTimeBetweenEvictionRunsMillis(-1);

            config.setTestOnReturn(true);

            // maxActive
            config.setMaxTotal(20);
            // maxWait
            config.setMaxWaitMillis(1000);

            pool = new JedisPool(config, ip,port,3000);
            maps.put(key,pool);
        }else{
           pool = maps.get(key);
        }

        return pool;
    }

    /**
     *  类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     *  没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class RedisUtilHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static JedisUtil instance = new JedisUtil();
    }

    public static JedisUtil getInstance(){
        return RedisUtilHolder.instance;
    }

    /**
     * 获取Redis实例.
     * @return Redis工具类实例
     */
    public Jedis getJedis(String ip , int port){
        Jedis jedis = null;
        int count = 0;
        do{
            try{
                jedis = getPool(ip,port).getResource();
            }catch (Exception e){
                LOOGER.error(e.getMessage(),e);
            }
            count ++;
        }while (jedis == null && count < 3);

        return jedis;
    }


   /**
    *  释放redis实例到连接池.
    * @param jedis redis实例
    */
    public void closeJedis(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }
}
