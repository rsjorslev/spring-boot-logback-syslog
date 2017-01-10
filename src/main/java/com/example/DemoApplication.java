package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

	@Scheduled(fixedDelay = 10000L)
	private static void logRandomStuff() {
		System.out.println("======================== LOG DEMO ENTRIES - BEGIN ===========================");
		LOGGER.debug("I am a debug entry and i am being generated automatically for demo purposes");
		LOGGER.info("I am a info entry and i am being generated automatically for demo purposes");
		LOGGER.warn("I am a warning entry and i am being generated automatically for demo purposes");
		LOGGER.error("I am a error entry and i am being generated automatically for demo purposes");
		LOGGER.trace("i am a trace entry and i am being generated automatically for demo purposes");
		System.out.println("======================== LOG DEMO ENTRIES - END ===========================");

	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


}
