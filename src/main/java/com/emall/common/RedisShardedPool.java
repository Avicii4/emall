package com.emall.common;

import com.emall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harry Chou
 * @date 2019/7/11
 */
public class RedisShardedPool {
    // Sharded Jedis连接池
    private static ShardedJedisPool pool;
    // 最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperties("redis.max.total", "20"));
    // 在JedisPool中最大的空闲状态的Jedis实例个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperties("redis.max.idle", "10"));
    // 在JedisPool中最大的空闲状态的Jedis实例个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperties("redis.min.idle", "2"));
    // Redis服务的IP
    private static String redis1Ip = PropertiesUtil.getProperties("redis1.ip");
    private static String redis2Ip = PropertiesUtil.getProperties("redis2.ip");
    // Redis的端口号
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperties("redis1.port"));
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperties("redis2.port"));
    // 使用一个Jedis实例时，是否要验证可用性，赋值为true时，得到的必为可用实例
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperties("redis.test.borrow", "true"));
    // 返还一个Jedis实例时，是否要验证可用性，赋值为true时，归还的必为可用实例
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperties("redis.test.return", "true"));

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        // 连接耗尽时是否阻塞，false抛出异常，true会阻塞到超时
        config.setBlockWhenExhausted(true);

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip,redis1Port,1000*2);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip,redis2Port,1000*2);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>(2);

        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        pool = new ShardedJedisPool(config,jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();
    }

    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        for (int i = 0; i < 10; i++) {
            jedis.set("key"+i, "value"+i);
        }
        returnResource(jedis);
        // for test
//        pool.destroy();
        System.out.println("THE END!");
    }
}
