package com.spodfy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpodfyApplication {

	private static final Logger logger = LogManager.getLogger(SpodfyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpodfyApplication.class, args);
	}

}
