package kerberos.spring.management.controller;

import kerberos.spring.management.UserAddressManagementApplication;
import kerberos.spring.management.entity.User;
import kerberos.spring.management.repository.UserRepository;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserAddressManagementApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestClientLiveTest {
    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    public String getURI() {
        return "http://localhost:" + port + "/";
    }
    public String getApiURI() {
        return getURI() + "api";
    }

    @Test
    public void whenUsersApiIsUp_then200OK() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getMethod = new HttpGet(getApiURI() + "/users");
        HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());

        Assert.assertEquals( HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenAddressApiIsUp_then200OK() throws IOException {
        User user = getSingleUser();

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getMethod = new HttpGet( getApiURI()  + "/addresses?userId=" + user.getId());
        HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());

        Assert.assertEquals( HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    private User getSingleUser() {return userRepository.findAll().stream().findFirst().get();}
}

