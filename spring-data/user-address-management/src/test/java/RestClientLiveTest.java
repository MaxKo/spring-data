import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;



@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ClientConfig.class}, loader = AnnotationConfigContextLoader.class)
public class RestClientLiveTest {

    final String uri = "http://localhost:8080/api";

    @Test
    public void whenUsersApiIsUp_then200OK() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getMethod = new HttpGet(uri + "/users");
        HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());

        Assert.assertEquals( HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenAddressApiIsUp_then200OK() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getMethod = new HttpGet( uri + "/addresses?userId=1");
        HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());

        Assert.assertEquals( HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

}

