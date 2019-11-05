package kerberos.spring.management.controller;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import kerberos.spring.management.UserAddressManagementApplication;
import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.User;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserAddressManagementApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestUserManagementTest {

    @LocalServerPort
    private int port;

    public String getURI() {
        return "http://localhost:" + port + "/";
    }
    public String getApiURI() {
        return getURI() + "api";
    }

    {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void whenCreateNewUser_thenCreated() throws IOException {
        final User user = createRandomUser();

        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .post(getApiURI() + "/user");

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        System.out.println(  IOUtils.toString(new ByteArrayInputStream(response.getBody().asByteArray()), StandardCharsets.UTF_8)  );// .htmlPath() .jsonPath() (("$.id") .as(UserDto.class);
        UserDto savedUser = response.getBody().as(UserDto.class);

        assertTrue(Long.valueOf(savedUser.getId()) > 0);

        final Response responseDelete = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(savedUser)
                .delete(getApiURI() + "/user");
        assertEquals(HttpStatus.NO_CONTENT.value(), responseDelete.getStatusCode());
    }

    @Test
    public void UserListSizeChangedWhenUserCreated() {
        final Response responseBeforeUsers = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(getApiURI() + "/users");
        List usersBefore = responseBeforeUsers.getBody().as(List.class);

        final User user = createRandomUser();

        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .post(getApiURI() + "/user");


        UserDto savedUser = response.getBody().as(UserDto.class);
        assertTrue(Long.valueOf(savedUser.getId()) > 0);

        final Response responseUsers = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(getApiURI() + "/users");
        List usersAfter = responseUsers.getBody().as(List.class);

        assertTrue(usersBefore.size() < usersAfter.size());

        //cleanup DB
        final Response responseDelete = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(savedUser)
                .delete(getApiURI() + "/user");
        assertEquals(HttpStatus.NO_CONTENT.value(), responseDelete.getStatusCode());
    }

    @Test
    public void UserNotFoundTest() {
        Response response = RestAssured.given()
                .get(getApiURI() + "/user/20392134221");

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void UserIdIncorrectFormat() {
        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(getURI() + "/users/1L");

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    private User createRandomUser() {
        User user = new User();
        user.setUsername(randomAlphabetic(10));

        return user;
    }
}
