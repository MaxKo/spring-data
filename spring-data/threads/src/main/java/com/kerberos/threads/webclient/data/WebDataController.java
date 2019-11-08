package com.kerberos.threads.webclient.data;

import com.kerberos.threads.client.WebMessage;
import com.kerberos.threads.webclient.Tweet;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
public class WebDataController {

    private static final int DEFAULT_PORT = 8080;


    @Autowired
    private MessageFactory messageFactory;

    @Setter
    private int serverPort = DEFAULT_PORT;

    @GetMapping("/data-blocking")
    public List<WebMessage> getDataBlocking() {
        log.info("Starting BLOCKING Controller!");
        final String uri = getSlowServiceUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<WebMessage>> response = restTemplate
                .exchange(
                      uri, HttpMethod.GET, null,
                      new ParameterizedTypeReference<List<WebMessage>>(){});

        List<WebMessage> result = response.getBody();
        result.forEach(m -> log.info(m.toString()));
        log.info("Exiting BLOCKING Controller!");

        return result;
    }

    @GetMapping(value = "/data-non-blocking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<WebMessage> getDataNonBlocking() {
        log.info("Starting NON-BLOCKING Controller!");
        Flux<WebMessage> tweetFlux = WebClient.create()
          .get()
          .uri(getSlowServiceUri())
          .retrieve()
          .bodyToFlux(WebMessage.class);

        tweetFlux.subscribe(tweet -> log.info(tweet.toString()));
        log.info("Exiting NON-BLOCKING Controller!");
        return tweetFlux;
    }

    @GetMapping("/data-single-blocking")
    public WebMessage getSingleDataBlocking() {
        log.info("Starting BLOCKING Controller!");
        final String uri = getSingleMessageServiceUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WebMessage> response = restTemplate
                .exchange(
                        uri, HttpMethod.GET, null,
                        new ParameterizedTypeReference<WebMessage>(){});

        WebMessage result = response.getBody();
        log.info(result.toString());
        log.info("Exiting BLOCKING Controller!");

        return result;
    }

    private String getSlowServiceUri() {
        return "http://localhost:" + serverPort + "/slow-service-data";
    }

    private String getSingleMessageServiceUri() {
        return "http://localhost:" + serverPort + "/service-single-message";
    }


}
