package com.spring.integration.sample.errorhandler;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

public class ErrorHandler {

	Logger log = LoggerFactory.getLogger(getClass());
	public void onError(Message<Serializable> msg) {
		log.error("payload : {}",  msg.getPayload());		
	}	
}
