package com.spring.integration.sample.filter;

import java.io.Serializable;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.spring.integration.sample.model.Hello;

@Service
public class FilterService {
	
	public boolean filter(Message<Serializable> message) {
		String json = (String) message.getPayload();
		return !json.contains("fail");
	}
}
