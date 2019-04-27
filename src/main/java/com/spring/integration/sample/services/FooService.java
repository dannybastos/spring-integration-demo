package com.spring.integration.sample.services;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FooService {
	Logger log = LoggerFactory.getLogger(getClass());
	public Message<Serializable> processMessage(Message<Serializable> message) {
		log.info("ID: {}, PAYLODAD: {}", message.getHeaders().get("ID"),message.getPayload());			
		return message;
	}
}
