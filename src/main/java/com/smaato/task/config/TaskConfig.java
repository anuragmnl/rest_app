package com.smaato.task.config;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableWebFlux
@Slf4j
public class TaskConfig {

  @Bean
  public Builder webClientBuilder(){
    HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofMillis(500));

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient));


  }

  @Bean
  public Map<String, List<Long>> requestMap(){
     return new ConcurrentHashMap<>();
  }

}
