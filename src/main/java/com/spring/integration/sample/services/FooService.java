package com.spring.integration.sample.services;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.integration.sample.model.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FooService {
	private final ObjectMapper mapper;

	public FooService(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

	Logger log = LoggerFactory.getLogger(getClass());
	public Serializable processMessage(Message<Serializable> message) throws IOException {
		String json = (String) message.getPayload();
		Hello hello = mapper.readValue(json, Hello.class);
		log.info("HEADER ID: {}, PAYLODAD: {}", message.getHeaders().get("ID"),hello);
		return json;
	}
}
