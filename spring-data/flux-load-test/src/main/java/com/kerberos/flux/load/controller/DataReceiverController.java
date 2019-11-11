package com.kerberos.threads.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Controller
public class DataReceiverController {

    @RequestMapping("data")
    public ResponseEntity<String> get(@RequestBody String body) throws IOException {

        String message = body + "\n";

       // System.out.println(message);

        Files.write(Paths.get("d://log.txt"), message.getBytes(), StandardOpenOption.APPEND);

        return ResponseEntity.ok("kerberos.done");
    }
}
