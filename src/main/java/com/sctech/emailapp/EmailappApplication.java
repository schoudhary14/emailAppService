package com.sctech.emailapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EmailappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailappApplication.class, args);
	}

}
