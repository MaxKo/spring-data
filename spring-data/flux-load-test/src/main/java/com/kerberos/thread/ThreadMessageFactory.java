package com.kerberos.thread;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@Scope("singleton")
public class ThreadMessageFactory  {

        public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd HH:mm:ss";
        volatile int counter = 0;

        private synchronized int getNextId() {
            return counter++;
        }

        int deayedFor = 0;

        private ThreadMessage getNextMessage() {
            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM_SS_Z);
            String time = formatter.format(ldt);
            int c = getNextId();
            return new ThreadMessage(c, "Message #:" + c + " of " + time + " thread: " + Thread.currentThread().getName() + " delayedFor: " + deayedFor, ldt);
        }

        public int getCounter() {
            return counter;
        }



        public ThreadMessage get() {
            deayedFor = 0;

            for(int i = 0; i < 2000000000; i++) {
                deayedFor++;
            }

            return getNextMessage();
        }
}
