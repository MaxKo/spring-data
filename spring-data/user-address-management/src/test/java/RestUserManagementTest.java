import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import kerberos.spring.management.UserAddressManagementApplication;
import kerberos.spring.management.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserAddressManagementApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestUserManagementTest {
    public static final String API_ROOT = RestClientLiveTest.API_ROOT;

    {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void whenCreateNewUser_thenCreated() {
        final User user = createRandomUser();

        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .post(API_ROOT + "/user");

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        User savedUser = response.getBody().as(User.class);
        System.out.println(savedUser);

        assertTrue(savedUser.getId() > 0);

        //cleanup DB
        final Response responseDelete = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(savedUser)
                .delete(API_ROOT + "/user");
        assertEquals(HttpStatus.NO_CONTENT.value(), responseDelete.getStatusCode());
    }

    @Test
    public void UserListSizeChangedWhenUserCreated() {

        final Response responseBeforeUsers = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(API_ROOT + "/users");
        List usersBefore = responseBeforeUsers.getBody().as(List.class);

        final User user = createRandomUser();

        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .post(API_ROOT + "/user");


        User savedUser = response.getBody().as(User.class);
        assertTrue(savedUser.getId() > 0);

        final Response responseUsers = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(API_ROOT + "/users");
        List usersAfter = responseUsers.getBody().as(List.class);

        assertTrue(usersBefore.size() < usersAfter.size());

        //cleanup DB
        final Response responseDelete = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(savedUser)
                .delete(API_ROOT + "/user");
        assertEquals(HttpStatus.NO_CONTENT.value(), responseDelete.getStatusCode());
    }

    @Test
    public void UserNotFoundTest() {
        Response response = RestAssured.given()
                .get("/users/2");

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void UserIdIncorrectFormat() {
        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(RestClientLiveTest.ROOT + "/users/1L");

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    private User createRandomUser() {
        User user = new User();
        user.setUsername(randomAlphabetic(10));
        return user;
    }
}
