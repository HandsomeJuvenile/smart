package com.ace.smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement // 开启事务
@SpringBootApplication //@Configuration ， @EnableAutoConfiguration 和 @ComponentScan 等价于这三个注解
@EnableScheduling //定时器
@EnableAsync // 异步
@ComponentScan(basePackages = {"com.example.smart"}) // 开启自动注解扫描
public class SmartApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartApplication.class, args);
	}
}
