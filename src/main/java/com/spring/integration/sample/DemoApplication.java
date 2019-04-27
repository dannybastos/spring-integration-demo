package com.spring.integration.sample;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication(scanBasePackages="com.spring.integration.sample")
@EnableIntegration
@ImportResource("classpath:integration-context.xml")
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	@Qualifier("atomicId")
	public AtomicInteger getId() {
		return new AtomicInteger();
	}
}
