package org.afive.wecheck.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * spring boot project start
 * @author ocko1
 *
 */
@SpringBootApplication
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
	
}
