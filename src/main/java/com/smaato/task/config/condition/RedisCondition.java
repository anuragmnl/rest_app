package com.smaato.task.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RedisCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    String val = context.getEnvironment().getProperty("smaato.application.redis.required");
    if(val!= null)
      return Boolean.parseBoolean(val);
    else
      return false;
  }
}
