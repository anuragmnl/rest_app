package com.smaato.task;

import com.smaato.task.client.KafkaAdminClient;
import com.smaato.task.config.KafkaConfigData;
import com.smaato.task.config.condition.KafkaCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class SmaatotaskApplication implements CommandLineRunner {

	private final KafkaConfigData kafkaConfigData;
	private final ApplicationContext applicationContext;

	@Autowired(required = false)
	private KafkaAdminClient kafkaAdminClient;

	public static void main(String[] args) {
		SpringApplication.run(SmaatotaskApplication.class, args);
	}


	@Override
	public void run(String... args) throws RuntimeException {
		if(kafkaAdminClient != null) {
			kafkaAdminClient.createTopic();
			kafkaAdminClient.checkSchemaRegistry();
			log.info("Topics [{}] ready", kafkaConfigData.getTopicNamesToCreate());
		}
	}
}
