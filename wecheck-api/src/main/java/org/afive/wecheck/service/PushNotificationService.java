package org.afive.wecheck.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.afive.wecheck.configuration.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PushNotificationService {
	
	@Async
	public static CompletableFuture<String> send(HttpEntity<String> entity){
		
		RestTemplate restTemplate = new RestTemplate();
		
		System.out.println("PushNotificationService restTemplate 만들었다.");
		
		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		
		interceptors.add(new HeaderRequestInterceptor("Authorization","key="+Data.FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type","application/json"));
	
		restTemplate.setInterceptors(interceptors);
		
		System.out.println("authorization : "+Data.FIREBASE_SERVER_KEY);


		String firebaseResponse = restTemplate.postForObject(Data.FIREBASE_API_URL, entity, String.class);
		
		System.out.println("firebaseResponse : "+firebaseResponse);
			
		return CompletableFuture.completedFuture(firebaseResponse);
	}
	
}
