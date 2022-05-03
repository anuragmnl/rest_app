package com.smaato.task.repository;

import java.util.Set;

public interface RedisRepository {

    void add(String key, Set<Long> value);

    Set<Long> get(String key);

    boolean remove(String key);

    boolean containsKey(String key);

    Long getSize(String key);

}

