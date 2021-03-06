package com.kerberos.flux.load.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebMessage {
    int id;
    String content;
    LocalDateTime date;

    public String toString() {
        return content;
    }
}
