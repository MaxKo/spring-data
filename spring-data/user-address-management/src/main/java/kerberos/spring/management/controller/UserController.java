package kerberos.spring.management.controller;

import kerberos.spring.management.controller.exception.UserNotFoundException;
import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.User;
import kerberos.spring.management.service.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired(required = true)
    private UserService userService;

    @Autowired(required = true)
    private MapperFacade mapper;

    @GetMapping("/user/{userId}")
    public UserDto getCustomerById(@PathVariable final Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(UserNotFoundException::new);

        UserDto userDto = mapper.map(user, UserDto.class);

        return  userDto;
    }

    @GetMapping(value = "/users",  produces = "application/json; charset=UTF-8")
    public Iterable<UserDto>  getAllUsers() {
        List<UserDto> result = new ArrayList<UserDto>();

        mapper.mapAsCollection(userService.getAllUsers(), result, UserDto.class);

        return result;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        User user1 = userService.save(user);
        return user1;
    }

    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody User user) {
        userService.delete(user);
    }
}
