package com.example.springbootdemo.common.utils.redisUtil;

import com.example.springbootdemo.service.DistributedLocker;
import com.example.springbootdemo.common.utils.SpringUtil;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;

import java.util.concurrent.TimeUnit;


/**
 * redis分布式锁帮助类
 * @author liushuai
 * @date 2024.01.12
 */
public class RedisLockUtil {

   // private static DistributedLocker distributedLocker = SpringContextHolder.getBean("distributedLocker", DistributedLocker.class);
    private static DistributedLocker distributedLocker = SpringUtil.getBean(DistributedLocker.class);

    /**
     * 加锁
     * @param lockKey
     * @return
     */
    public RLock lock(String lockKey) {
        return distributedLocker.lock(lockKey);
    }

    /**
     * 释放锁
     * @param lockKey
     */
    public static void unlock(String lockKey) {
        distributedLocker.unlock(lockKey);
    }

    /**
     * 尝试获取锁
     * @param lockKey
     * @param unit
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放时间
     * @return
     */
    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime){
        return distributedLocker.tryLock(lockKey, unit, waitTime, leaseTime);
    }

    public static RCountDownLatch getCountDownLatch(String name) {
        return distributedLocker.getCountDownLatch(name);
    }

    /**
     * 获取信号量
     * @param name
     * @return
     */
    public static RSemaphore getSemaphore(String name) {
        return distributedLocker.getSemaphore(name);
    }


}
