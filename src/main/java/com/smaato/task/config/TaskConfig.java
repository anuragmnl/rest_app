package com.smaato.task.config;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableWebFlux
@EnableRedisRepositories
@Slf4j
public class TaskConfig {

  @Value("${smaato.application.redis.hostname}")
  private String hostname;
  @Value("${smaato.application.redis.port}")
  private int port;

  @Bean
  public WebClient webClient(WebClient.Builder builder){
    HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofMillis(500));
    return builder.clientConnector(new ReactorClientHttpConnector(httpClient)).build();
  }

  @Bean
  public Map<String, List<Long>> requestMap(){
     return new ConcurrentHashMap<>();
  }


  @Bean
  public LettuceConnectionFactory lettuceConnectionFactory(){
    RedisStandaloneConfiguration redisStandaloneConfiguration =new RedisStandaloneConfiguration(hostname,port);
    LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder().commandTimeout(Duration.ofMillis(1000)).build();
    return new LettuceConnectionFactory(redisStandaloneConfiguration,lettuceClientConfiguration);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    final RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(lettuceConnectionFactory());
    template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
    template.afterPropertiesSet();
    return template;
  }

}
