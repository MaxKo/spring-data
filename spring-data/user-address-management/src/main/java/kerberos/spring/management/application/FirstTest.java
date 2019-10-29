package kerberos.spring.management.application;

import kerberos.spring.management.dao.UserDao;
import kerberos.spring.management.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
@ComponentScan(basePackages = "kerberos.spring.management")
@EnableJpaRepositories("kerberos.spring.management.dao")
@EntityScan("kerberos.spring.management.entity")
public class FirstTest {

    @Autowired
    UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(FirstTest.class, args);

        System.out.println("test ok bar" );
    }

    @Bean
    CommandLineRunner init(UserDao userRepository) {
        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {

                User user = new User(name);

                new Random().ints()
                        .limit(2 + new Random().nextInt(7))
                        .forEach(i -> {
                            System.out.println("future addrsss:" + i);/*
                            Address address = new Address(
                                    name + "'s address",
                                    new Locale(
                                            "",
                                            Locale.getISOCountries()[new Random().nextInt(Locale.getISOCountries().length)])
                                            .getDisplayCountry()
                            );
                            address.setUser(user); */
                            //user.getAddresses().add(address);
                        });
                userRepository.save(user);
            });
            userRepository.findAll().forEach(System.out::println);
            //addressRepository.findAll().forEach(System.out::println);
        };
    }

}
