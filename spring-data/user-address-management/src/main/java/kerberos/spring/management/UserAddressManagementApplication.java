package kerberos.spring.management;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import kerberos.spring.management.entity.Address;
import kerberos.spring.management.entity.User;
import kerberos.spring.management.repository.UserRepository;

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
                user.setBirthDate(generateRandomBirthDate());
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

    private LocalDateTime generateRandomBirthDate() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        return  LocalDateTime.of(LocalDate.ofEpochDay(randomDay), LocalTime.NOON);
    }
}
