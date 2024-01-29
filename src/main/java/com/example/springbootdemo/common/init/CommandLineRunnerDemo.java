package com.example.springbootdemo.common.init;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

@Component
@Order(value = 2)
public class CommandLineRunnerDemo implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        //可以默认去数据库建表
        System.out.println("CommandLineRunner的作用就是，SpringBoot启动成功之后，我就立马做点事情," +
                "主要功能就是项目启动成功之后，ApplicationRunner默认比CommandLineRunner优先执行");

        System.out.println("CommandLineRunner拿到的是最原始的参数，你传的是什么参数，这里拿到的就是什么参数:" + Arrays.toString(args));

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();



    }

//    private void initDb(DruidDataSource druidDataSource, String initFileName, String tableName) throws SQLException {
//
//        DruidPooledConnection connection = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        connection = druidDataSource.getConnection();
//
//        stmt = connection.createStatement();
//        //判断表是否存在
//        rs = connection.getMetaData().getTables(null, null, tableName, null);
//    }

}
