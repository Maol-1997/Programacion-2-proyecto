package ar.edu.um.programacion2.principal.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.edu.um.programacion2.principal.service.dto.LogDTO;
import ar.edu.um.programacion2.principal.service.dto.LogListDTO;
import ar.edu.um.programacion2.principal.service.util.PostUtil;

@Service
public class LogService {
	public ResponseEntity<List<LogDTO>> getAllLogs() throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		PostUtil jwtClass = new PostUtil();
		restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
			
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				jwtClass.getJwt(false);
				request.getHeaders().set("Authorization", "Bearer " + jwtClass.getJwt_log());
				return execution.execute(request, body);
			}
		});
		//HttpResponse response = PostUtil.sendGet("http://127.0.0.1:8082/api/log/");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept (Arrays.asList(MediaType.APPLICATION_JSON));
		jwtClass.getJwt(false);

        headers.set("Authorization", "Bearer " + jwtClass.getJwt_log());
        HttpEntity<String> entity = new HttpEntity<String>("", headers);		
        ResponseEntity<List<LogDTO>> result = restTemplate.exchange("http://127.0.0.1:8082/api/log/",HttpMethod.GET,entity, new ParameterizedTypeReference<List<LogDTO>>() {});
		return result;
	}
}
