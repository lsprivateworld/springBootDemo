package com.example.springbootdemo.common.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(value = 1)
public class ApplicationRunnerDemo implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner的作用就是，SpringBoot启动成功之后，我就立马做点事情,主要功能就是项目启动成功之后，" +
                "ApplicationRunner默认比CommandLineRunner优先执行");

        System.out.println("ApplicationArguments类的getSourceArgs()方法获取到的是最原始的参数: " +
                "你传的是什么参数，这里拿到的就是什么参数:->" + Arrays.toString(args.getSourceArgs()));

        System.out.println("ApplicationArguments类的getOptionNames()方法获取到的是参数的key值->: " + args.getOptionNames());
        System.out.println("如果传递的参是这样的：--foo=bar --debug,那么getOptionNames()获取到的参数是这样的[\"foo\", \"debug\"]----> " + args.getOptionNames());
        System.out.println("ApplicationArguments类的getNonOptionArgs方法获取到的参数都是没用--开头传递的参数->: " + args.getNonOptionArgs());

        String key = "test";
        if (args.containsOption(key)) {
            System.out.println("如果存在--test这个参数，把test参数的值取出来给大家伙看一下:" + args.getOptionValues(key));
        }

        String noKey = "noKey";
        if (!args.containsOption(noKey)) {
            System.out.println("兄弟们不存在--noKey这个参数" );
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    }
}
