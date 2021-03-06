package com.smaato.task.config;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableWebFlux
@EnableRedisRepositories
@Slf4j
@Import({RetryConfigData.class,KafkaConfigData.class,KafkaProducerConfigData.class
, KafkaAdminConfig.class,KafkaProducerConfig.class,RetryConfig.class,RedisConfig.class})
public class TaskConfig {

  @Bean
  public WebClient webClient(WebClient.Builder builder){
    HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofMillis(500));
    return builder.clientConnector(new ReactorClientHttpConnector(httpClient)).build();
  }

  @Bean
  public Map<String, List<Long>> requestMap(){
     return new ConcurrentHashMap<>();
  }

}
