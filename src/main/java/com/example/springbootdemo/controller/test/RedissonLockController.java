package com.example.springbootdemo.controller.test;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/redisson")
@Slf4j
public class RedissonLockController {

    /**
     * 锁测试共享变量
     */
    private Integer lockCount = 10;

    /**
     * 无锁测试变量
     */
    private Integer count = 10;

    /**
     * 模拟线程数
     */
    private static int threadNum = 10;

    /**
     * 模拟并发测试加锁和不加锁
     */
    @GetMapping("/test")
    @ApiOperation(value = "模拟并发测试加锁和不加锁")
    public void lock() {
        //计数器
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threadNum; i++) {
           // MyRunnable myRunnable = new MyRunnable();
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {

                }
            };
        }
    }

}
