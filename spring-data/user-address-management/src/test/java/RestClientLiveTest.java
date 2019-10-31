import kerberos.spring.management.UserAddressManagementApplication;
import org.junit.Assert;
//import org.springframework.http.HttpStatus;

import org.apache.http.HttpStatus;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ClientConfig.class}, loader = AnnotationConfigContextLoader.class)
//@WebIntegrationTest(randomPort=true)
//@SpringApplicationConfiguration(UserAddressManagementApplication.class)
@SpringBootTest(classes = { UserAddressManagementApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class RestClientLiveTest {
    public static final String ROOT = "http://localhost:8080/";
    public static final String API_ROOT = ROOT + "api";


    @Test
    public void whenUsersApiIsUp_then200OK() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getMethod = new HttpGet(API_ROOT + "/users");
        HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());

        Assert.assertEquals( HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenAddressApiIsUp_then200OK() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getMethod = new HttpGet( API_ROOT + "/addresses?userId=1");
        HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());

        Assert.assertEquals( HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }


}

