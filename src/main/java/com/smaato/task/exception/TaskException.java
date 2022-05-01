package com.smaato.task.exception;

public class TaskException extends RuntimeException{

  public TaskException(){
    super();
  }

  public TaskException(String message){
    super(message);
  }

  public TaskException(String message,Throwable throwable){
    super(message,throwable);
  }

}
