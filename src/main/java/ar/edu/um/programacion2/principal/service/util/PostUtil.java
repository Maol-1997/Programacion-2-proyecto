package ar.edu.um.programacion2.principal.service.util;

import ar.edu.um.programacion2.principal.PrincipalApp;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class PostUtil {
    private static String jwt = PrincipalApp.jwt; //JWT TOKEN

    public static String postTarjeta(String payload, String url) throws IOException {
        StringEntity entity = new StringEntity(payload,
            ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        request.addHeader("Authorization","Bearer "+jwt);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }
}
