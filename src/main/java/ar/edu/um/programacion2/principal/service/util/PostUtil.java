package ar.edu.um.programacion2.principal.service.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class PostUtil {
    private static String jwt = ""; //JWT TOKEN

    public static String sendPost(String payload, String url) throws IOException {
        HttpResponse response;
        boolean flag;
        do {
            StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.addHeader("Authorization", "Bearer " + jwt);
            request.setEntity(entity);
            response = httpClient.execute(request);
            flag = response.getStatusLine().toString().contains("401");
            if(flag)
            getJwt();
        }while(flag);


        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    public static void getJwt() throws IOException {
        StringEntity entity = new StringEntity("{\n" +
            "\"login\": \"system\",\n" +
            "\"pass\": \"system\"\n" +
            "}",
            ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://127.0.0.1:8081/login/");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        jwt = EntityUtils.toString(response.getEntity(),"UTF-8");
    }
}
