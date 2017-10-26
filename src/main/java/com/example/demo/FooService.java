package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FooService {
	Logger log = LoggerFactory.getLogger(getClass());
	public Message<String> processMessage(Message<String> message) {
		log.info(String.format("ID=%s, PAYLODAD:%s", message.getHeaders().get("ID"),message.getPayload()));
		return message;
	}
}
