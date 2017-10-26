package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

public class BarService {
	Logger log = LoggerFactory.getLogger(getClass());
	public void sendMail(Message<String> message) {
		log.info(String.format("PUB => ID=%s, PAYLODAD:%s", message.getHeaders().get("ID"),message.getPayload()));
	}
}
