package com.ggz.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @author ggz
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.ggz.server.mapper")
@EnableScheduling
public class YebApplication {

	public static void main(String[] args) {
		SpringApplication.run(YebApplication.class,args);
	}

}