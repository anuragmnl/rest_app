package com.smaato.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class SmaatotaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmaatotaskApplication.class, args);
	}

}
