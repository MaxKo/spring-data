package kerberos.spring.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kerberos.spring.management.config.JsonSerializerBean;
import kerberos.spring.management.controller.exception.UserNotFoundException;
import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.User;
import kerberos.spring.management.service.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
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
    private MapperFacade mapper;

    @Autowired(required = true)
    private JsonSerializerBean js;

    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getCustomerById(@PathVariable final Long userId) throws JsonProcessingException {
        User user = userService.getUserById(userId)
                .orElseThrow(UserNotFoundException::new);

        UserDto userDto = mapper.map(user, UserDto.class);

        return ResponseEntity.ok(js.toJson(userDto));
    }

    @GetMapping(value = "/users",  produces = "application/json;")
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

        String response = js.toJson(savedDtoUser);

        return new ResponseEntity<String> (response, HttpStatus.CREATED);
    }

    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody UserDto userDto) {
        User user = mapper.map(userDto, User.class);

        userService.delete(user);
    }
    

}
