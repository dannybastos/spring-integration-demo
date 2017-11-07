package com.example.demo;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

public class ErrorHandler {

	Logger log = LoggerFactory.getLogger(getClass());
	public void onError(Message<Serializable> msg) {
		if (log.isInfoEnabled() )
			log.error(String.format("payload : %s",  msg.getPayload()));		
	}	
}
