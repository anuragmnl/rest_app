package com.smaato.task.config;

import com.smaato.task.config.condition.KafkaCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@Conditional(KafkaCondition.class)
@RequiredArgsConstructor
public class RetryConfig {

  private final RetryConfigData retryConfigData;

  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();
    ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
    exponentialBackOffPolicy.setMaxInterval(retryConfigData.getMaxIntervalMs());
    exponentialBackOffPolicy.setInitialInterval(retryConfigData.getInitiaLIntervalMs());
    exponentialBackOffPolicy.setMultiplier(retryConfigData.getMultiplier());

    retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);

    SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
    simpleRetryPolicy.setMaxAttempts(retryConfigData.getMaxAttempts());
    retryTemplate.setRetryPolicy(simpleRetryPolicy);

    return retryTemplate;
  }
}
