package kerberos.spring.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import kerberos.spring.management.UserAddressManagementApplication;
import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.User;
import kerberos.spring.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserAddressManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
class UserControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext wac;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    void getAllUsers() throws Exception {
        User userRequest = getSingleUser();

        mvc.perform(get("/api/users")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].id").value(userRequest.getId()))
                .andExpect(jsonPath("$[0].username").value(userRequest.getUsername()));;
    }

    @Test
    public void givenId_whenUriIsPerson_thenGetPerson() {
        User userRequest = getSingleUser();

        ResponseEntity<UserDto> response = restTemplate
                .getForEntity("/api/user/" + userRequest.getId(), UserDto.class);
        UserDto user = response.getBody();

        assertEquals(userRequest.getId().toString(), user.getId());
        assertEquals(userRequest.getUsername(), user.getUsername());
    }

    @Test
    public void getUserById() throws Exception {
        User user = getSingleUser();

        mvc.perform(get("/api/user/" + user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()));;
    }

    private User getSingleUser() {return  userRepository.findAll().stream().findFirst().get(); }
}