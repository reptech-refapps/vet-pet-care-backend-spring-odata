package com.mycompany.group234.utils;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NotificationClient {

	public static String sendEmail(Map<String, Object> bodyParams) throws JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper jsonObjectMapper = new ObjectMapper();
		String locationUrl = null;

		try {
			String apiUrl = "http://localhost:8081/app/notification";
			String payload = jsonObjectMapper.writeValueAsString(bodyParams);

			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");

			HttpEntity<String> httpEntity = new HttpEntity<>(payload, headers);

			ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, httpEntity, String.class);

			if (response.getStatusCode() == HttpStatus.CREATED) {
				locationUrl = response.getHeaders().getFirst("Location");
				System.out.println("API Response: " + locationUrl);
			}
		} catch (Exception e) {
			System.out.println("Error caused: " + e.getMessage());
		}
		return locationUrl;
	}
}
