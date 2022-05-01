package com.smaato.task.exception.handler;

import static com.smaato.task.constant.TaskConstant.FAILED;

import com.smaato.task.exception.TaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class TaskExceptionHandler {

  @ExceptionHandler(TaskException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleTaskExcpetion(TaskException ex){
    log.info(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> handleRuntimeException(RuntimeException ex){
    log.info(ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> handleGenericExcpetion(Exception ex){
    log.info(ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED);
  }
}
