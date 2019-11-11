package com.kerberos.flux.load.webclient.data;

import com.kerberos.flux.load.client.WebMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @GetMapping("/service-10000-messages")
    private Flux<WebMessage> get10000Messages() throws Exception {
        return Flux.range(0, 10000).map(i -> messageFactory.getNextMessage());
    }

    @GetMapping("/service-get-amount-messages")
    private Flux<WebMessage> getAmountMessages(@RequestParam int amount) throws Exception {
        return Flux.range(0, amount).map(i -> messageFactory.getNextMessage());
    }
}
