package test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author y-time
 * @version 1.0
 * @date 2021-07-22 10:50
 */
public class RedisTest {

    @Test
    public void redisTest() {
        // 与Redis建立连接 IP+port，（云服务器的公网ip）
        Jedis redis = new Jedis("112.74.80.80", 6379);
        // 在Redis中写字符串 key value
        redis.set("jedis:name:1", "jd-zhangfei");
        // 获得Redis中字符串的值
        System.out.println(redis.get("jedis:name:1"));
        // 在Redis中写list
        redis.lpush("jedis:list:1", "1", "2", "3", "4", "5");
        // 获得list的长度
        System.out.println(redis.llen("jedis:list:1"));
    }
}
