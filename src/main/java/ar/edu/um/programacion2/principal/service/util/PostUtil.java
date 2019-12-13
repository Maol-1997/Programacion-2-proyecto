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
            request.addHeader("Authorization", "Bearer " + jwt);
            request.setEntity(entity);
            response = httpClient.execute(request);
            flag = response.getStatusLine().toString().contains("401");
        	System.out.println(flag);
        	System.out.println(port);

            if(flag) {
                if(port) {
                	getJwt(8081);
                } else {
                	getJwt(8082);
                }
            }
        }while(flag);

        return response;
        //return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    public static void getJwt(Integer port) throws IOException {
        StringEntity entity = new StringEntity("{\n" +
            "\"login\": \"system\",\n" +
            "\"pass\": \"system\"\n" +
            "}",
            ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        System.out.println("http://127.0.0.1:"+port+"/login/");
        HttpPost request = new HttpPost("http://127.0.0.1:"+port+"/login/");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        jwt = EntityUtils.toString(response.getEntity(),"UTF-8");
    }
}
