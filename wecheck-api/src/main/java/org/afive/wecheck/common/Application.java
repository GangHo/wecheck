package org.afive.wecheck.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

/**
 * spring boot project start
 * @author ocko1
 *
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"org.afive.wecheck"})
@MapperScan("org.afive.wecheck")
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}
		
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
//	/* -- 단일 스레드인 경우 --*/
//	@Bean
//	public TaskScheduler taskScheduler() {
//		return new ConcurrentTaskScheduler();
//	}
}
