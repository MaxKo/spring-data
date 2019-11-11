package com.kerberos.thread;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ThreadsApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThreadMessageFactoryTest {
    @Autowired
    private ThreadMessageFactory tmf;

    @Test
    public void testBlockingNext() {
        System.out.println(tmf.get());
    }

    @Test
    public void testNonBlockingNext() {
        CompletableFuture.supplyAsync(() ->{
            System.out.println("Processing in separate thread");
            return tmf.get();
        }).whenCompleteAsync((result, exc) -> System.out.println(result));
    }
    @Test
    public void testNonBlocking10000() throws InterruptedException {
        Thread.sleep(10000);

        int amount = 1000;

        int amBefore = tmf.getCounter();

        ExecutorService pool = Executors.newFixedThreadPool(5);

        IntStream.range(0, amount).forEach(i -> {
            CompletableFuture.supplyAsync(() ->{
                System.out.println("Processing in separate thread #" + i);
                return tmf.get();
            }, pool).whenCompleteAsync((result, exc) -> System.out.println(result));
        });

        Thread.sleep(10000);

        int amAfter = tmf.getCounter();

        Assert.assertEquals(amount, amAfter - amBefore);
    }


    @Test
    public void testNonBlockingFlux1000() throws InterruptedException {
        Thread.sleep(10000);

        int amount = 1000;

        int amBefore = tmf.getCounter();

        Flux.range(0, amount)
                .parallel(5)
                .runOn(Schedulers.parallel())
                .map(i -> tmf.get())
                .subscribe(System.out::println);

        Thread.sleep(10000);

        int amAfter = tmf.getCounter();

        Assert.assertEquals(amount, amAfter - amBefore);
    }

}