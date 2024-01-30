package com.example.springbootdemo.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在应用程序的任何地方调用MyLogger类的logMessage()方法，就会生成相应的日志消息。
 * 注意事项：
 * MyLogger类需要与其他组件或服务共享同一个包路径（package），以确保正确获取到日志记录器。
 * 默认情况下，Logback将根据配置文件（如logback.xml）设置日志级别、格式等属性
 */
public class MyLogger {
    private static final Logger logger = LoggerFactory.getLogger("myLog");

    public static void logMessage() {
        // 使用logger对象记录日志消息
        logger.info("这是一条自定义的日志信息,这里可以单独做日志的自定义（格式、逻辑等的处理）");
        logger.debug("fdf");
    }
}