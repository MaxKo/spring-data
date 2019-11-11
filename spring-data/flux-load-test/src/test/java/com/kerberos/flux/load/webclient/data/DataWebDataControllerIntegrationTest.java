package com.kerberos.flux.load.webclient.data;

import com.kerberos.flux.load.client.WebMessage;
import com.kerberos.flux.load.webclient.WebClientApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebClientApplication.class)
public class DataWebDataControllerIntegrationTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private WebDataController webDataController;

    @Before
    public void setup() {
        webDataController.setServerPort(randomServerPort);
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

        List<WebMessage> webMessages = testClient.get()
                .uri("/data-by-amount-non-blocking?amount=" + amount)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(WebMessage.class)
                .hasSize(amount)
                .returnResult()
                .getResponseBody();

        webMessages.stream().forEach(System.out::println);

        System.out.println("Duration of: " + amount + " is " + Duration.between(ldtStart, LocalDateTime.now()));

        List<WebMessage> duplicates = getDuplicates(webMessages);

        Assert.assertEquals(0, duplicates.size());
    }

    public static List<WebMessage> getDuplicates(final List<WebMessage> webMessages) {
        return getDuplicatesMap(webMessages).values().stream()
                .filter(duplicates -> duplicates.size() > 1)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static Map<Integer, List<WebMessage>> getDuplicatesMap(List<WebMessage> webMessages) {
        return webMessages.stream().collect(Collectors.groupingBy(DataWebDataControllerIntegrationTest::uniqueAttributes));
    }


    private static int uniqueAttributes(WebMessage message){
        if(Objects.isNull(message)){
            return -1;
        }

        return (message.getId());
    }
}