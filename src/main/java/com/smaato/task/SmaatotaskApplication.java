package com.smaato.task;

import com.smaato.task.client.KafkaAdminClient;
import com.smaato.task.config.KafkaConfigData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class SmaatotaskApplication implements CommandLineRunner {

	private final KafkaAdminClient kafkaAdminClient;
	private final KafkaConfigData kafkaConfigData;

	public static void main(String[] args) {
		SpringApplication.run(SmaatotaskApplication.class, args);
	}


	@Override
	public void run(String... args) throws RuntimeException {
		kafkaAdminClient.createTopic();
		kafkaAdminClient.checkSchemaRegistry();
		log.info("Topics [{}] ready",kafkaConfigData.getTopicNamesToCreate());
	}
}
