package com.smaato.task.exception;

public class EndPointNotAvailableException extends RuntimeException{

  public EndPointNotAvailableException(){
    super();
  }

  public EndPointNotAvailableException(String message){
    super(message);
  }

  public EndPointNotAvailableException(String message,Throwable throwable){
    super(message,throwable);
  }


}
