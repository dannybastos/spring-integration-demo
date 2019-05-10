package com.spring.integration.sample.rest;

import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.integration.sample.model.Hello;

@RestController
public class RestSampleService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final ObjectMapper mapper;
    
    public RestSampleService(ObjectMapper mapper) {
    	super();
    	this.mapper = mapper;
    }	
	
	@PostMapping(value = "httpservice", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Serializable> httpservice(@RequestBody String message) throws IOException {
		Hello model = mapper.readValue(message, Hello.class);
        log.info(model.toString());
        if (model.getMsg().contains("fail"))
        	return ResponseEntity.badRequest().body("BUSINNESS ERROR");
		return ResponseEntity.ok(mapper.writeValueAsBytes(model));
	}

}
