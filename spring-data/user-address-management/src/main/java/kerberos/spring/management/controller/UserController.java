package kerberos.spring.management.controller;

import kerberos.spring.management.entity.User;
import kerberos.spring.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired(required = true)
    private UserService userService;

/*
    @GetMapping("/users")
    public String getAllUsers(@RequestParam(value = "key") String key) {
        //return userService.getAllUsers().toString();
        return "sss";
    }
*/

    @GetMapping("/user/{userId}")
    public Optional<User> getCustomerById(@PathVariable final Long userId) {
        return userService.getUserById(userId);
    }



    @GetMapping("/users")
    public Iterable<User>  getAllUsers() {
        return userService.getAllUsers();
    }


}
