package com.kerberos.flux.load.webclient.data;

import com.kerberos.flux.load.client.WebMessage;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.logging.Level;

@Component
@Scope("singleton")
@Log
public class MessageFactory implements Supplier<WebMessage> {
    public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd HH:mm:ss";
    int counter = 0;


    int temp;
    private int artificialDelay(){
       // int threadshold = 200000;

        temp = 0;

        for(int i = 0; i < (2000 - counter) * 40; i++){
            temp ++;
        }

        return temp;
    }

    public WebMessage getNextMessage() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM_SS_Z);
        String time = formatter.format(ldt);

        /*int temp =*/ artificialDelay();

        String messageContent = "Message #:" + counter++ + " of " + time + " delayed for:" + temp + " thread" + Thread.currentThread().getName();

        log.log(Level.ALL, "MessageFactory:" + messageContent);

        return new WebMessage(counter, messageContent, ldt);
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
