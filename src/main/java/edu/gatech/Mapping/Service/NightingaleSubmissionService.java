package edu.gatech.Mapping.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class NightingaleSubmissionService {

	private RestTemplate restTemplate;
	@Value("${nightingale.url}")
	private String nightingaleURL;
	
	public NightingaleSubmissionService() {
		this.restTemplate = new RestTemplate();
	}
	
	public JsonNode submitRecord(String POSTendpoint, String VRDRJson) {
		System.out.println("Submitting to nightingale at endpoint:" + POSTendpoint);
		JsonNode response = JsonNodeFactory.instance.objectNode();
		ResponseEntity<String> POSTresponse
		  = restTemplate.postForEntity(POSTendpoint, VRDRJson, String.class);
		if(POSTresponse.getStatusCode() != HttpStatus.OK) {
			return response;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			response = mapper.readTree(POSTresponse.getBody());
		} catch (IOException e) {
			return response;
		}
		return response;
	}

	public String getNightingaleURL() {
		return nightingaleURL;
	}

	public void setNightingaleURL(String nightingaleURL) {
		this.nightingaleURL = nightingaleURL;
	}
}