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
    private static String jwt_log = ""; //JWT TOKEN LOGS
    private static String jwt_tarjeta = ""; //JWT TOKEN TARJETAS

    public static HttpResponse sendPost(String payload, String url) throws IOException {
        HttpResponse response;
        boolean flag;
        boolean port;
        do {
            port = url.contains("8081");

            StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            if(port)
                request.addHeader("Authorization", "Bearer " + jwt_tarjeta);
            else
                request.addHeader("Authorization", "Bearer " + jwt_log);
            request.setEntity(entity);
            response = httpClient.execute(request);
            flag = response.getStatusLine().toString().contains("401");
            if(flag) {
                getJwt(port);
            }
        }while(flag);
        return response;
    }

    public static void getJwt(boolean port) throws IOException {
        StringEntity entity = new StringEntity("{\n" +
            "\"login\": \"system\",\n" +
            "\"pass\": \"system\"\n" +
            "}",
            ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request;
        if(port) {
            request = new HttpPost("http://127.0.0.1:8081/login/");
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);
            jwt_tarjeta = EntityUtils.toString(response.getEntity(),"UTF-8");
        }
        else {
            request = new HttpPost("http://127.0.0.1:8082/login/");
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);
            jwt_log = EntityUtils.toString(response.getEntity(),"UTF-8");
        }
    }
}
