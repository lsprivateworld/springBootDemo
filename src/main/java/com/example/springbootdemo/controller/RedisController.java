package com.example.springbootdemo.controller;

import com.example.springbootdemo.config.RedissonConfig;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 扣减库存
     * 用redisson解决原子性操作问题
     * 分段锁也可以提升性能
     * @return
     */
    @RequestMapping("/deduct_stock")
    public String deductStock() {
        RedissonConfig redissonClient = new RedissonConfig();
        RedissonClient redisson = redissonClient.redissonClient();
        String lockKey = "product_101";
//        String clientId = UUID.randomUUID().toString();
//        //分布式锁 ，相当于setNx
//        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
//        if (!result) {
//            return "error_code";
//        }
        RLock redissonLock = redisson.getLock(lockKey);
        try {
            //加锁
            redissonLock.lock();//setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS)
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock")); //jedis.get("stock");
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else {
                System.out.println("扣减失败，库存不足：");
            }
        } finally {
            //释放锁
//            if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
//                stringRedisTemplate.delete(lockKey);
//            }
            redissonLock.unlock();
        }
        return "end";
    }

    /**
     * redlock
     */
    @RequestMapping("/redlock")
    public String redlock() {

        RedissonConfig redissonClient = new RedissonConfig();
        RedissonClient redisson = redissonClient.redissonClient();
        String lockKey = "product_101";
        //这里需要自己去实例化不同redisson客户端连接，这里这是一个为代码
        RLock lock1 = redisson.getLock(lockKey);
        RLock lock2 = redisson.getLock(lockKey);
        RLock lock3 = redisson.getLock(lockKey);

        //根据多个Rlock对象构建RedissonRedLock
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);

        try {
            boolean res = redLock.tryLock(10, 30, TimeUnit.SECONDS);
            if (res) {
                System.out.println("成功获取到锁");
                //做业务逻辑
            }
        } catch (Exception e) {
            throw  new RuntimeException("lock fail");
        } finally {
            redLock.unlock();
        }
        return "end";
    }
    /**
     * 数据库与缓存双写不一致问题
     * 方案：延时双删：写数据库，删缓存，sleep,再删缓存
     * 方案：分布式锁：读写锁（把一个锁对象拆成一个读锁，一个写锁） 读写互斥、写写互斥、读读不互斥 （适合读多写少的场景）
     */
    //读取库存
    @RequestMapping("/get_stock")
    public String getStock(@RequestParam("clientId") Long clientId) throws InterruptedException {
        RedissonConfig redissonClient = new RedissonConfig();
        RedissonClient redisson = redissonClient.redissonClient();
        String lockKey = "product_stock_101";

        RReadWriteLock readWriteLock = redisson.getReadWriteLock(lockKey);
        RLock rLock = readWriteLock.readLock();

        rLock.lock();
        System.out.println("获取读锁成功，clientId = " + clientId);
        String stock = stringRedisTemplate.opsForValue().get("stock");
        if (StringUtils.isEmpty(stock)) {
            System.out.println("查询数据库库存为10。。。");
            Thread.sleep(1000);
            stringRedisTemplate.opsForValue().set("stock", "10");
        }
        rLock.unlock();
        System.out.println("释放读锁成功：clientId = " + clientId);
        return "end";
    }

    //更新库存
    @RequestMapping("/update_stock")
    public String updateStock(@RequestParam("clientId") Long clientId) throws InterruptedException {
        RedissonConfig redissonClient = new RedissonConfig();
        RedissonClient redisson = redissonClient.redissonClient();

        String lockKey = "product_stock_101";

        RReadWriteLock readWriteLock = redisson.getReadWriteLock(lockKey);
        RLock writeLock = readWriteLock.writeLock();

        writeLock.lock();
        System.out.println("获取写锁成功，clientId = " + clientId);
        System.out.println("修改商品为101的数据库库存为6");
        stringRedisTemplate.delete("stock");
        Thread.sleep(1000);
        writeLock.unlock();
        System.out.println("释放写锁成功：clientId = " + clientId);

        return "end";
    }

    /**
     * 读多写多场景的解决方案
     * canal中间件（监听binlog日志，同步写入redis）
     * 基于数据库增量日志解析，提供增量数据订阅&消费

     * https://blog.csdn.net/Aruioooo/article/details/130728753
     * https://blog.csdn.net/yy139926/article/details/127768446
     * 实现步骤：
     * 一、1、mysql开启binlog日志
     *    2、配置完成后重启mysql服务
     *    3、使用root账户创建mysql的canal用户并赋予权限
     * 二、安装canal
     * 三、添加pom依赖
     * 1、<!-- canal -->
     * <dependency>
     *     <groupId>com.alibaba.otter</groupId>
     *     <artifactId>canal.client</artifactId>
     *     <version>1.1.6</version>
     * </dependency>
     * <dependency>
     *     <groupId>com.alibaba.otter</groupId>
     *     <artifactId>canal.protocol</artifactId>
     *     <version>1.1.6</version>
     * </dependency>
     * 2、application配置文件添加canal配置，ip改为canal部署的服务器的ip
     * # canal
     * canal:
     *   server:
     *     ip: 127.0.0.1
     *     port: 11111
     *   promotion:
     *     destination: example
     * 3、创建canal监听类
     * 4、测试，修改数据库数据，canal监听到数据库数据操作记录
     */
}

