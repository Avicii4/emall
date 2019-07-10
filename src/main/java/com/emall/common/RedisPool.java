package com.emall.common;

import com.emall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Harry Chou
 * @date 2019/7/9
 */
public class RedisPool {
    // Jedis连接池
    private static JedisPool pool;
    // 最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperties("redis.max.total", "20"));
    // 在JedisPool中最大的空闲状态的Jedis实例个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperties("redis.max.idle", "10"));
    // 在JedisPool中最大的空闲状态的Jedis实例个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperties("redis.min.idle", "2"));
    // Redis服务的IP
    private static String redisIp = PropertiesUtil.getProperties("redis.ip");
    // Redis的端口号
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperties("redis.port"));
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

        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("key", "value");
        returnResource(jedis);
        // for test
        pool.destroy();
        System.out.println("THE END!");
    }

}
