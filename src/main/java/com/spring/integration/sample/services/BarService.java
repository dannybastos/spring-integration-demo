package com.spring.integration.sample.services;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;

import com.spring.integration.sample.model.Hello;

public class BarService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final ObjectMapper mapper;

	public BarService(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

    @Autowired
    @Qualifier("atomicId")
    private AtomicInteger atomicId;
    
	public Serializable sendMail(Message<Serializable> message) throws IOException {
		String json = (String) message.getPayload();
		Hello hello = mapper.readValue(json, Hello.class);
		hello.setId(atomicId.getAndIncrement());
		log.info("PUB => ID:{}, PAYLODAD:{}", message.getHeaders().get("ID"), hello);
		json = mapper.writeValueAsString(hello);
		return json;
	}
}
