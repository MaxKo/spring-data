package com.kerberos.threads.webclient.data;

import com.kerberos.threads.client.WebMessage;
import com.kerberos.threads.webclient.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class MessageDataSlowServiceController {

    @Autowired
    private MessageFactory messageFactory;


    @GetMapping("/slow-service-data")
    private List<WebMessage> getAllMessages() throws Exception {
        Thread.sleep(2000L); // delay

        return IntStream.range(0, 5).mapToObj(messageFactory::getNextMessage).collect(Collectors.toList());
    }

    @GetMapping("/service-single-message")
    private Mono<WebMessage> getSingleMessage() throws Exception {
        return Mono.just(messageFactory.getNextMessage());
    }
}
