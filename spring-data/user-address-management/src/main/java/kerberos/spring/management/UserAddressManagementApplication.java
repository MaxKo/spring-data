package kerberos.spring.management;

import kerberos.spring.management.repository.UserRepository;
import kerberos.spring.management.entity.Address;
import kerberos.spring.management.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class UserAddressManagementApplication {
    final static Logger logger = Logger.getLogger(UserAddressManagementApplication.class);

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserAddressManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        if (userRepository.findAll().size() > 0) return args -> {};

        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {

                User user = new User(name);

                new Random().ints()
                        .limit(2 + new Random().nextInt(7))
                        .forEach(i -> {
                            System.out.println("future address:" + i);
                            Address address = new Address(
                                    name + "'s address",
                                    new Locale(
                                            "",
                                            Locale.getISOCountries()[new Random().nextInt(Locale.getISOCountries().length)])
                                            .getDisplayCountry()
                            );
                            address.setUser(user);
                            user.getAddresses().add(address);
                        });
                userRepository.save(user);
            });

            logger.debug(userRepository.findAll());
        };
    }

}
