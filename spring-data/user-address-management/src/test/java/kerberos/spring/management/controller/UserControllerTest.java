package kerberos.spring.management.controller;




import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import kerberos.spring.management.UserAddressManagementApplication;
import kerberos.spring.management.entity.User;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

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
      //          .addFilter(springSecurityFilterChain);
    }


    @Test
    void getAllUsers() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        //ResponseEntity<String> response = restTemplate.exchange(("/api/users"), HttpMethod.GET, entity, String.class);
        ResponseEntity<String> response = restTemplate.exchange(URI.create("http://localhost:" + port + "/api/users"), HttpMethod.GET, entity, String.class);

        String expected = "[{\"id\":1,\"username\":\"John\"}, {}, {}, {}, {}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    public void givenId_whenUriIsPerson_thenGetPerson() {
        ResponseEntity<User> response = restTemplate
                .getForEntity("/api/user/1", User.class);
        User user = response.getBody();

        assertEquals(1L, user.getId());
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