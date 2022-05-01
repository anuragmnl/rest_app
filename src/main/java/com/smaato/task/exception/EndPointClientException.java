package com.smaato.task.exception;

public class EndPointClientException extends RuntimeException{

  public EndPointClientException(){
    super();
  }

  public EndPointClientException(String message){
    super(message);
  }

}
