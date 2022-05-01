package com.smaato.task.exception.handler;

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
    /**
     * Will implement in some time
     */
    log.info(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");

  }

}
