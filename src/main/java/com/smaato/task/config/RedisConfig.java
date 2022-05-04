package com.smaato.task.config;

import com.smaato.task.config.condition.RedisCondition;
import java.time.Duration;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
@Conditional(RedisCondition.class)
public class RedisConfig {

  @Value("${smaato.application.redis.hostname}")
  private String hostname;
  @Value("${smaato.application.redis.port}")
  private int port;

  @Bean
  public LettuceConnectionFactory lettuceConnectionFactory(){
    RedisStandaloneConfiguration redisStandaloneConfiguration =new RedisStandaloneConfiguration(hostname,port);
    LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder().commandTimeout(
        Duration.ofMillis(1000)).build();
    return new LettuceConnectionFactory(redisStandaloneConfiguration,lettuceClientConfiguration);
  }

  @Bean
  public RedisTemplate<String, Set<Long>> redisTemplate() {
    final RedisTemplate<String, Set<Long>> template = new RedisTemplate<>();
    template.setConnectionFactory(lettuceConnectionFactory());
    template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
    template.afterPropertiesSet();
    return template;
  }

}
