package com.kerberos.threads.webclient.data;

import com.kerberos.threads.client.WebMessage;
import com.kerberos.threads.webclient.WebClientApplication;
import com.kerberos.threads.webclient.WebClientController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebClientApplication.class)
public class DataWebDataControllerIntegrationTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private WebClientController webClientController;

    @Before
    public void setup() {
        webClientController.setServerPort(randomServerPort);
    }

    @Test
    public void whenEndpointWithBlockingClientIsCalled_thenFiveMessagesAreReceived() {
        testClient.get()
          .uri("/data-blocking")
          .exchange()
          .expectStatus()
          .isOk()
          .expectBodyList(WebMessage.class)
          .hasSize(5);
    }

    @Test
    public void whenEndpointWithBlockingClientIsCalled_thenSingleAreReceived() {
        testClient.get()
                .uri("/data-single-blocking")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(WebMessage.class)
                .hasSize(1);
    }


    @Test
    public void whenEndpointWithNonBlockingClientIsCalled_thenThreeTweetsAreReceived() {
        testClient.get()
          .uri("/data-non-blocking")
          .exchange()
          .expectStatus()
          .isOk()
          .expectBodyList(WebMessage.class)
          .hasSize(5);
    }
}