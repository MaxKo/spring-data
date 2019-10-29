package kerberos.spring.management.controller;

import kerberos.spring.management.controller.exception.UserNotFoundException;
import kerberos.spring.management.entity.User;
import kerberos.spring.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
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
    public User getCustomerById(@PathVariable final Long userId) {
        return userService.getUserById(userId)
                .orElseThrow(UserNotFoundException::new);
    }



    @GetMapping(value = "/users",  produces = "application/json; charset=UTF-8")
    public Iterable<User>  getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User delete(@RequestBody User user) {
        User user1 = userService.save(user);
        return user1;
    }

    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody User user) {
        userService.delete(user);
    }


}
