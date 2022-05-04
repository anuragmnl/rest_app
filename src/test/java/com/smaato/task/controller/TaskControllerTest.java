package com.smaato.task.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.smaato.task.service.TaskService;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class TaskControllerTest {

  public static final String V_1_SMAATO = "/api/smaato";

  @Autowired
  WebTestClient webTestClient;

  @Autowired
  Environment environment;

  @Autowired
  TaskService taskService;

  @BeforeEach
  void setUp() {
    
  }

  @Test
  void testAccept_withIdParamOnly_andReturnOk() {

    int random =ThreadLocalRandom.current().nextInt(1,Integer.MAX_VALUE);

    var uri= UriComponentsBuilder.fromUriString(V_1_SMAATO+"/accept")
        .queryParam("id",random).buildAndExpand().toUri();


    webTestClient
        .get()
        .uri(uri)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(String.class)
        .isEqualTo("ok");
  }

  @Test
  void testAccept_withIdParamOnly_andReturnFailed() {

    int random =ThreadLocalRandom.current().nextInt(1,Integer.MAX_VALUE);

    var uri= UriComponentsBuilder.fromUriString(V_1_SMAATO+"/accept")
        .queryParam("id",random).buildAndExpand().toUri();


    webTestClient
        .get()
        .uri(uri)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(String.class)
        .isEqualTo("ok");

    webTestClient
        .get()
        .uri(uri)
        .exchange()
        .expectStatus()
        .is4xxClientError()
        .expectBody(String.class)
        .isEqualTo("failed");
  }


  @Test
  void testAccept_withIdAndUriParam_andReturnOk() {

    int random =ThreadLocalRandom.current().nextInt(1,Integer.MAX_VALUE);

    String port =environment.getProperty("local.server.port");

    var uri= UriComponentsBuilder.fromUriString(V_1_SMAATO+"/accept")
        .queryParam("id",random).
    queryParam("uri",String.format("http://localhost:%s/api/smaato/count",port)).buildAndExpand().toUri();


    webTestClient
        .get()
        .uri(uri)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(String.class)
        .isEqualTo("ok");

    WireMock.verify(1,postRequestedFor(urlEqualTo(String.format("http://localhost:%s/api/smaato/count?count=1",port))));
  }

  @Test
  void testAccept_withIdAndUriParam_andReturnFailed() {

    int random =ThreadLocalRandom.current().nextInt(1,Integer.MAX_VALUE);

    var uri= UriComponentsBuilder.fromUriString(V_1_SMAATO+"/accept")
        .queryParam("id",random).buildAndExpand().toUri();


    webTestClient
        .get()
        .uri(uri)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(String.class)
        .isEqualTo("ok");

    webTestClient
        .get()
        .uri(uri)
        .exchange()
        .expectStatus()
        .is4xxClientError()
        .expectBody(String.class)
        .isEqualTo("failed");
  }

  @AfterEach
  void tearDown() {
    
  }
}
