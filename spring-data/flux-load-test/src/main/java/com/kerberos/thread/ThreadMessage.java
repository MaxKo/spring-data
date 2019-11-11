package com.kerberos.thread;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ThreadMessage {
    private int id;
    private  String message;
    private LocalDateTime createdAt;
}
