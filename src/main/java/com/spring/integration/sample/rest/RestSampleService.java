package com.spring.integration.sample.rest;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.integration.sample.model.HelloModel;

@RestController
public class RestSampleService {
	Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("atomicId")
    private AtomicInteger atomicId;
	
	@PostMapping("httpservice")
	public ResponseEntity<Serializable> httpservice(@RequestBody String message) {
        HelloModel model = new HelloModel(message,atomicId.getAndIncrement());
        log.info(model.toString());
        if (model.getMsg().contains("falha"))
        	return ResponseEntity.badRequest().body("ERRO");
		return ResponseEntity.ok(model);
	}	
}
