package com.kerberos.flux.load.client;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

@Component
@Scope("singleton")
public class StringMessageFactory implements Supplier<String> {
    public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd HH:mm:ss nnnnnnnnn";
    int counter = 0;

    public String getNextMessage() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM_SS_Z);
        String time = formatter.format(ldt);

        return "Message #:" + counter++ + " of " + time;
    }

    public int getCounter() {
        return counter;
    }

    public String getNextMessage(String s) {
        return s + getNextMessage();
    }

    @Override
    public String get() {
        return getNextMessage();
    }
}
