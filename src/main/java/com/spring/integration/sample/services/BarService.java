package com.spring.integration.sample.services;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.spring.integration.sample.model.HelloModel;

public class BarService {
	Logger log = LoggerFactory.getLogger(getClass());
	public void sendMail(Message<Serializable> message) {
		String msg = null;
		if (message.getPayload() instanceof HelloModel) {
			msg = ((HelloModel) message.getPayload()).toString();
		} else {
			msg = (String) message.getPayload();
		}		
		log.info("PUB => ID:{}, PAYLODAD:{}", message.getHeaders().get("ID"),msg);
	}
}
