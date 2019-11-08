package com.kerberos.threads.webclient.data;

import com.kerberos.threads.client.WebMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

@Component
@Scope("singleton")
public class MessageFactory implements Supplier<WebMessage> {
    public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd HH:mm:ss nnnnnnnnn";
    int counter = 0;


    public WebMessage getNextMessage() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM_SS_Z);
        String time = formatter.format(ldt);

        return new WebMessage("Message #:" + counter++ + " of " + time, ldt);
    }

    public int getCounter() {
        return counter;
    }

    public String getNextMessage(String s) {
        return s + getNextMessage();
    }

    @Override
    public WebMessage get() {
        return getNextMessage();
    }

    public WebMessage getNextMessage(int i) {
        return getNextMessage();
    }
}
