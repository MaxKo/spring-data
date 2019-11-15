package com.kerberos.flux.load.controller;

import com.kerberos.thread.ThreadsApplication;
import com.kerberos.flux.load.client.StringMessageFactory;
import com.kerberos.flux.load.client.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ThreadsApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataReceiverControllerTest {

    @Autowired
    StringMessageFactory mf;// = new MessageFactory();

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;


    @BeforeEach
    public void before() throws IOException {
        String file = "d://log.txt";

        Files.deleteIfExists(Paths.get(file));

        Files.createFile(Paths.get(file));
    }

    @Test
    public void getSimple() {
        String s = restTemplate.postForObject("/data", mf.getNextMessage(), String.class);

        System.out.println(s);
    }

    @Test
    public void post10SecPortion() {
        LocalDateTime ldtStart = LocalDateTime.now();

        Duration d;
        do {
            IntStream.range(0, 10).forEach(i -> {
                restTemplate.postForObject("/data", mf.getNextMessage(), String.class);
            });

            d = Duration.between(ldtStart, LocalDateTime.now());
        } while(d.getSeconds() < 10);

        System.out.println(d.getSeconds() + " " +d.getNano());

        System.out.println("Messages sent: " + mf.getCounter());
    }


    @Test
    public void post10secPortionWithRestClientMultyThread() throws InterruptedException {
        LocalDateTime ldtStart = LocalDateTime.now();

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ThreadsApplication.class);

        RestClient thread1 = (RestClient) ctx.getBean("restClient");// new Thread(new RestClient());
        thread1.start();
        RestClient thread2 = (RestClient) ctx.getBean("restClient");// new Thread(new RestClient());
        thread2.start();

        Thread.sleep(12000);

        Duration d = Duration.between(ldtStart, LocalDateTime.now());

        System.out.println(d.getSeconds() + " " +d.getNano());

        System.out.println("Messages sent: " + mf.getCounter());
    }



    @Test
    public void post10secPortionWithRestClient() throws InterruptedException {


        WebClient client = WebClient
                .builder()
                .baseUrl("http://localhost:" + port)
                // .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                //.defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
                .build();


        LocalDateTime ldtStart = LocalDateTime.now();

        Duration d;
        Function<String, String> messageInterface =  mf::getNextMessage;
            //IntStream.range(0, 10).forEach(i -> {
               client
                        .method(HttpMethod.POST)
                        .uri("/data");
                        //.body(BodyInserters.fromPublisher(Mono.just(messageInterface)), String.class);//  BodyInserters.fromValue(mf.getNextMessage()))
                       //.body(BodyInserters.fromPublisher());
                      //  .retrieve()
                       // .bodyToMono(String.class)
                       // .subscribe(s -> System.out.println(s));
          // });

            d = Duration.between(ldtStart, LocalDateTime.now());


        System.out.println(d.getSeconds() + " " +d.getNano());

        System.out.println("Thread finishing:" + this + "Messages after: " + mf.getCounter());
    }
}