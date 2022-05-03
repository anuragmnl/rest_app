package com.smaato.task.exception;

public class KafkaClientException extends
    RuntimeException {

  public KafkaClientException(){

  }

  public KafkaClientException(String message){
    super(message);
  }

  public KafkaClientException(String s, Throwable t) {
    super(s,t);
  }
}
