package com.example.springbootdemo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * redisson配置，下面是单节点配置
 */
@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public  RedissonClient redissonClient() {
        Config config = new Config();
        //单节点
        System.out.println(host);
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        //多节点
        /*config.useMasterSlaveServers()
                .setMasterAddress()
                .addSlaveAddress()
                .addSlaveAddress();
         */
        config.useSingleServer().setPassword("ls140078");
//        if (StringUtils.isEmpty(password)) {
//            config.useSingleServer().setPassword(null);
//        } else {
//            config.useSingleServer().setPassword("ls140078");
//        }

        return Redisson.create(config);
    }
}
