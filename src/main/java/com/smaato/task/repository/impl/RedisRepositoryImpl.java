package com.smaato.task.repository.impl;

import com.smaato.task.config.condition.RedisCondition;
import com.smaato.task.repository.RedisRepository;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Slf4j
@Repository
@Conditional(RedisCondition.class)
public class RedisRepositoryImpl implements RedisRepository {

  private static final String REDIS_KEY =  "REQUEST_IDS";

  private final RedisTemplate<String, Set<Long>> redisTemplate;

  private HashOperations<String,String,Object> hashOperations = null;

  @PostConstruct
  private void init(){
    if(redisTemplate != null)
      hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public void add(String key, Set<Long> value) {
    hashOperations.put(REDIS_KEY,key,value);
  }

  @Override
  public Set<Long> get(String key) {
    return (Set<Long>) hashOperations.get(REDIS_KEY,key);
  }



  @Override
  public boolean remove(String key) {
    hashOperations.delete(REDIS_KEY,key);
    return get(key) == null;
  }

  @Override
  public boolean containsKey(String key) {
    return hashOperations.hasKey(REDIS_KEY,key);
  }

  @Override
  public Long getSize(String key) {
    return (long) get(key).size();
  }
}
