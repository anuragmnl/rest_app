package com.smaato.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class SmaatotaskApplicationTests {

	@Autowired
	Environment environment;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(environment);
	}

}
