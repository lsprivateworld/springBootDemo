package com.example.springbootdemo.common.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(value = 2)
public class CommandLineRunnerDemo implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner的作用就是，SpringBoot启动成功之后，我就立马做点事情," +
                "主要功能就是项目启动成功之后，ApplicationRunner默认比CommandLineRunner优先执行");

        System.out.println("CommandLineRunner拿到的是最原始的参数，你传的是什么参数，这里拿到的就是什么参数:" + Arrays.toString(args));

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    }
}
