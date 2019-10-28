package kerberos.spring.management.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstTest {
    public static void main(String[] args) {
        SpringApplication.run(FirstTest.class, args);

        System.out.println("test ok");
    }

}
