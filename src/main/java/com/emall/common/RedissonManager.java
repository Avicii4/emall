package com.emall.common;

import com.emall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Harry Chou
 * @date 2019/7/14
 */
@Component
@Slf4j
public class RedissonManager {

    private Config config = new Config();

    private Redisson redisson = null;

    // Redis服务的IP
    private static String redis1Ip = PropertiesUtil.getProperties("redis1.ip");
    private static String redis2Ip = PropertiesUtil.getProperties("redis2.ip");
    // Redis的端口号
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperties("redis1.port"));
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperties("redis2.port"));

    public Redisson getRedisson(){
        return redisson;
    }

    @PostConstruct
    private void init(){
        try {
            config.useSingleServer().setAddress(new StringBuilder().append(redis1Ip).append(":").append(redis1Port).toString());
            redisson = (Redisson) Redisson.create(config);
            log.info("初始化Redisson结束");
        } catch (Exception e){
            log.error("Redisson init error",e);
        }
    }
}
