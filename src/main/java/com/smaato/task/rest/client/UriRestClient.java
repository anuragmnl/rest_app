package com.smaato.task.rest.client;

import static com.smaato.task.constant.TaskConstant.FAILED;
import static com.smaato.task.constant.TaskConstant.OK;
import static com.smaato.task.constant.TaskConstant.TIME_KEY;

import com.smaato.task.exception.EndPointClientException;
import com.smaato.task.exception.EndPointNotAvailableException;
import com.smaato.task.service.TaskService;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UriRestClient {

  private final WebClient webClient;
  private final TaskService taskService;

  public Mono<String> callEndPoint(String uri) {
    String presentTime = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_KEY));
    int count = taskService.count(presentTime);

    String uriString = UriComponentsBuilder
        .fromHttpUrl(uri)
        .queryParam("count",count)
        .buildAndExpand()
        .toUriString();
    log.info("calling url [{}]",uriString);

    return webClient
              .post()
              .uri(uriString)
              .retrieve()
        .onStatus(HttpStatus::is4xxClientError,clientResponse -> {
          if(clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)){
            return Mono.error(new EndPointClientException(FAILED));
          }
          return clientResponse.bodyToMono(String.class)
              .flatMap(responseMsg -> Mono.error(new EndPointClientException(responseMsg)));
        })
        .onStatus(HttpStatus::is5xxServerError,clientResponse ->
            clientResponse.bodyToMono(String.class)
                .flatMap(responseMsg -> Mono.error(new EndPointNotAvailableException(FAILED)))
        )
        .bodyToMono(String.class)
        .thenReturn(OK);

  }
}
