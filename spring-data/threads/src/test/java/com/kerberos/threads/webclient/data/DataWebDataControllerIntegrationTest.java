package com.kerberos.threads.webclient.data;

import com.kerberos.threads.client.WebMessage;
import com.kerberos.threads.webclient.WebClientApplication;
import com.kerberos.threads.webclient.WebClientController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WebClientApplication.class)
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
    public void whenEndpointWithNonBlockingClientIsCalled_thenFiveMessagesAreReceived() {
        testClient.get()
          .uri("/data-non-blocking")
          .exchange()
          .expectStatus()
          .isOk()
          .expectBodyList(WebMessage.class)
          .hasSize(5);
    }

    @Test
    public void whenEndpointWithNonBlockingClientIsCalled_thenSingleMessagesAreReceived() {
        testClient.get()
                .uri("/data-single-non-blocking")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(WebMessage.class)
                .hasSize(1);
    }

    @Test
    public void whenEndpointWithNonBlockingClientIsCalled_thenLoadTestFor10Sec() {
        testClient.get()
                .uri("/data-single-10sec-non-blocking")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(WebMessage.class)
                .hasSize(1);
    }

    @Test
    public void whenEndpointWithNonBlockingClientIsCalled_thenLoadTestFor10000Messages() {
        testClient.get()
                .uri("/data-10000-non-blocking")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(WebMessage.class)
                .hasSize(10000);
    }

    @Test
    public void whenEndpointWithNonBlockingClientIsCalled_thenLoadBy10000Messages() {
        testClient.get()
                .uri("/data-by-amount-non-blocking?amount=10000")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(WebMessage.class)
                .hasSize(10000);
    }

    @Test
    @AutoConfigureWebTestClient(timeout = "36000")
    public void whenEndpointWithNonBlockingClientIsCalled_thenLoadBy1000Messages() {
        int amount = 1000;
        LocalDateTime ldtStart = LocalDateTime.now();

        List<WebMessage> resp = testClient.get()
                .uri("/data-by-amount-non-blocking?amount=" + amount)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(WebMessage.class)
                .hasSize(amount)
                .returnResult()
                .getResponseBody();

        resp.stream().forEach(System.out::println);

        System.out.println("Duration of: " + amount + " is " + Duration.between(ldtStart, LocalDateTime.now()));
    }
}