package kerberos.spring.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import kerberos.spring.management.UserAddressManagementApplication;
import kerberos.spring.management.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserAddressManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
class UserControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    private MockMvc mvc;

    private ObjectMapper objectMapper;
    private ObjectWriter objectWriter;

    @Autowired
    private WebApplicationContext wac;

    HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();

        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    void getAllUsers() throws Exception {
        mvc.perform(get("/api/users")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].username").value("John"));;
    }

    @Test
    public void givenId_whenUriIsPerson_thenGetPerson() {
        ResponseEntity<UserDto> response = restTemplate
                .getForEntity("/api/user/1", UserDto.class);
        UserDto user = response.getBody();

        assertEquals("1", user.getId());
        assertEquals("John", user.getUsername());
    }

    @Test
    public void getUserById() throws Exception {
        mvc.perform(get("/api/user/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("John"));;
    }
}