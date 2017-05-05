package com.example.controller;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

	@RequestMapping(value="/download/file", method=RequestMethod.GET)
	public void viewFile() {

		String link = "http://www.teste.com.br/arquivo.xls";
		RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		
        // TODO SE PRECISAR DE AUTENTICACAO
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("username", "password"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
				
		HttpEntity<String> entity = new HttpEntity<String>(headers);

	    ResponseEntity<byte[]> response = restTemplate.exchange(       
	    		link, HttpMethod.GET, entity, byte[].class, "1");

	    try {
	    	if (response.getStatusCode() == HttpStatus.OK) {
	    		FileUtils.writeByteArrayToFile(new File("/temp/arquivo.xls"), response.getBody());
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	}
	
}
