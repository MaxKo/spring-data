package com.kerberos.flux.load.client;


import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Scope("prototype")
public class RestClient extends Thread {
    WebClient client = WebClient
            .builder()
            .baseUrl("http://localhost:8080/data")
           // .defaultCookie("cookieKey", "cookieValue")
            //.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            //.defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
            .build();

    @Autowired(required = true)
    StringMessageFactory mf;

    public void run() {
        System.out.println("Thread starting:"  + this + "Messages before: " + mf.getCounter());

        LocalDateTime ldtStart = LocalDateTime.now();

        Duration d;
        do {
            IntStream.range(0, 10).forEach(i -> {
                Mono<String> resp = client
                        .method(HttpMethod.POST)
                       // .uri("/data")
                        .body(BodyInserters.fromValue(mf.getNextMessage()))
                        .retrieve()
                        .bodyToMono(String.class);
                resp.subscribe(s -> System.out.println(s));
                });

            d = Duration.between(ldtStart, LocalDateTime.now());
        } while(d.getSeconds() < 10);

        System.out.println(d.getSeconds() + " " +d.getNano());

        System.out.println("Thread finishing:" + this + "Messages after: " + mf.getCounter());
    }
}
