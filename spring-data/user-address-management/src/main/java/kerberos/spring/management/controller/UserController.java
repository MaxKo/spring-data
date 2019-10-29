package kerberos.spring.management.controller;

import kerberos.spring.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/users")
    public String getAllUsers() {
        return userService.getAllUsers().toString();
    }


}
