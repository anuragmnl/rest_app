package com.smaato.task.service;

import java.io.Serializable;

public interface KafkaProducerService<K extends Serializable,V extends Serializable> {
    void send(String topic,K key,V message);
}
