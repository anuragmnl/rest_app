package com.smaato.task.rest.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class UriRestClient {

  private final WebClient.Builder webBuilder;

  public void callEndPoint(String uri) {

    /**
     * WIll implement in some time
     */
  }
}
