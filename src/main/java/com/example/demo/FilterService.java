package com.example.demo;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
	
	public boolean filter(Message<String> message) {
		if (message.getHeaders().get("ID").toString().equals("20")) {
			return true;
		}
		return false;
	}
}
