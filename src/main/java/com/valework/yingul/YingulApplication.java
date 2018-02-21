package com.valework.yingul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YingulApplication {

	public static void main(String[] args) {
		SpringApplication.run(YingulApplication.class, args);
	}
}
