package kerberos.spring.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kerberos.spring.management.applicaion.config.JsonSerializer;
import kerberos.spring.management.controller.exception.UserNotFoundException;
import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.User;
import kerberos.spring.management.service.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired(required = true)
    private UserService userService;

    @Autowired(required = true)
    @Qualifier("user")
    private MapperFacade mapper;

    @Autowired(required = true)
    private JsonSerializer js;

    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getCustomerById(@PathVariable final Long userId) throws JsonProcessingException {
        User user = userService.getUserById(userId)
                .orElseThrow(UserNotFoundException::new);

        UserDto userDto = mapper.map(user, UserDto.class);

        return ResponseEntity.ok(js.toJson(userDto));
    }

    @GetMapping(value = "/users",  produces = "application/json; charset=UTF-8")
    public ResponseEntity<String>  getAllUsers() throws JsonProcessingException {
        List<UserDto> result = new ArrayList<UserDto>();

        mapper.mapAsCollection(userService.getAllUsers(), result, UserDto.class);

        return ResponseEntity.ok(js.toJson(result));
    }

    @PostMapping("/user")
    public ResponseEntity<String> create(@RequestBody UserDto userDto) {
        User user = mapper.map(userDto, User.class);

        User savedUser = userService.save(user);

        UserDto savedDtoUser = mapper.map(savedUser, UserDto.class);

        return new ResponseEntity (js.toJson(savedDtoUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody UserDto userDto) {
        User user = mapper.map(userDto, User.class);

        userService.delete(user);
    }
}
