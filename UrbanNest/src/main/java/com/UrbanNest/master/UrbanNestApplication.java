package com.UrbanNest.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UrbanNestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrbanNestApplication.class, args);
	}

}
