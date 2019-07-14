package com.emall.task;

import com.emall.common.Const;
import com.emall.common.RedissonManager;
import com.emall.service.IOrderService;
import com.emall.util.PropertiesUtil;
import com.emall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @author Harry Chou
 * @date 2019/7/13
 */
@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private RedissonManager redissonManager;

    @PreDestroy
    public void delLock() {
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
    }

    // 每一分钟检查关单
//    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask1() {
        log.info("关闭订单,定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperties("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        log.info("关闭订单,定时任务结束");
    }

    //    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask2() {
        log.info("关闭订单,定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperties("lock.timeout", "50000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            // 设置成功，可获取锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获得分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单,定时任务结束");
    }

        @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask3() {
        log.info("关闭订单,定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperties("lock.timeout", "50000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));

        if (setnxResult != null && setnxResult.intValue() == 1) {
            // 设置成功，可获取锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            //未获取到锁
            String lockValueStr = RedisShardedPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
                String getSetResult = RedisShardedPoolUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
                if (getSetResult == null || (getSetResult != null && StringUtils.equals(lockValueStr, getSetResult))) {
                    // 真正获取到锁
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("没有获得分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            } else {
                log.info("没有获得分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);

            }
        }
        log.info("关闭订单,定时任务结束");
    }

//    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask4() {
        RLock rLock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        boolean getLock = false;
        try {
            if (getLock = rLock.tryLock(0, 50, TimeUnit.SECONDS)) {
                log.info("Redisson获取分布式锁:{}, ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
                int hour = Integer.parseInt(PropertiesUtil.getProperties("close.order.task.time.hour", "2"));
                iOrderService.closeOrder(hour);
            } else {
                log.info("Redisson未获得分布式锁:{}, ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redisson分布式锁获取异常", e);
        } finally {
            if (!getLock) {
                return;
            }
            rLock.unlock();
            log.info("Redisson分布式释放锁");
        }
    }

    private void closeOrder(String lockName) {
        // 设置锁的有效期
        RedisShardedPoolUtil.expire(lockName, 5);
        log.info("获取{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperties("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("===============================");
    }
}
