package com.example.demo;

import java.io.Serializable;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
	
	public boolean filter(Message<Serializable> message) {
		if (message.getPayload().toString().equals("20")) {
			return true;
		}
		return false;
	}
}
