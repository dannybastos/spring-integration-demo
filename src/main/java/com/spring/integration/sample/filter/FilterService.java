package com.spring.integration.sample.filter;

import java.io.Serializable;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
	
	public boolean filter(Message<Serializable> message) {
		return message.getPayload().toString().equals("20");
	}
}
