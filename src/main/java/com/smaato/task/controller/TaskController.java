package com.smaato.task.controller;

import static com.smaato.task.constant.TaskConstant.OK;
import static com.smaato.task.constant.TaskConstant.TIME_KEY;

import com.smaato.task.exception.TaskException;
import com.smaato.task.rest.client.UriRestClient;
import com.smaato.task.service.TaskService;
import com.smaato.task.util.TaskUtil;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/smaato")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final UriRestClient uriRestClient;
    private final TaskService service;

    @GetMapping("/accept")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> accept(@RequestParam Long id,@RequestParam(required = false) String uri) throws TaskException {
          log.info("Recieved new request with param id= [{}],uri = [{}] ",id,uri);
          service.persist(id, LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_KEY)));
          if( uri != null && !uri.isBlank() && !uri.isEmpty() ){
              if(!TaskUtil.isUrlValid(uri)){
                throw new TaskException("Invalid uri");
              }
           return uriRestClient.callEndPoint(uri);
          }
          return Mono.just(OK);
    }

  @PostMapping("/count")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> count(@RequestParam Long count) throws TaskException {
    log.info("Recieved new request with param count= [{}] ",count);
    return Mono.just(OK);
  }

}
