package com.smaato.task.config;

import com.smaato.task.config.condition.KafkaCondition;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@Configuration
@RequiredArgsConstructor
@Conditional(KafkaCondition.class)
public class KafkaAdminConfig {
  private final KafkaConfigData kafkaConfigData;

  @Bean
  public AdminClient adminClient(){
    return AdminClient.create(
        Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,kafkaConfigData.getBootstrapServers()));
  }
}
