package jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Component:
 * Description:
 * Date: 15/11/1
 *
 * @author yue.zhang
 */
public class JedisTest{

    @Test
    public void writeString(){

        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.set("book1","thinking java");

        String value = jedis.get("book1");
        System.out.println(value);


    }



}
