package com.kerberos.threads.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebMessage {
    String content;
    LocalDateTime date;

    public String toString() {
        return content;
    }
}
