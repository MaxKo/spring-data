package com.kerberos.deffered;

import com.kerberos.flux.load.webclient.WebClientApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
@EnableWebFluxSecurity
public class DefferedWebApp {
    public static void main(String[] args) {
        SpringApplication.run(DefferedWebApp.class, args);
    }

    @Bean
    public SecurityWebFilterChain functionalValidationsSpringSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .anyExchange()
                .permitAll();
        http.csrf().disable();

        return http.build();
    }
}
