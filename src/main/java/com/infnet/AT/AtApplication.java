package com.infnet.AT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AtApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtApplication.class, args);
	}

}
