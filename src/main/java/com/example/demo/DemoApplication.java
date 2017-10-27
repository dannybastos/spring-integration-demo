package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages="com.example.demo")
@EnableIntegration
@RestController
@ImportResource("classpath:integration-context.xml")
public class DemoApplication {

	@Autowired
	@Qualifier("channel1")
	private MessageChannel channel1;
	@Autowired
	@Qualifier("channel2")
	private MessageChannel channel2;
	
	@Autowired
	@Qualifier("httpChannel")
	private MessageChannel httpChannel;
	
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@GetMapping("channel1/{ID}")
	public String channel1(@PathVariable("ID") String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("ID", id);
		MessageHeaders messageHeaders = new MutableMessageHeaders(map);
//		channel1.send(MessageBuilder.createMessage("teste", messageHeaders));
		httpChannel.send(MessageBuilder.createMessage(
				"{\n" + 
				"	\"ID\":"+id+"\n" + 
				"	,\"MESSAGE\":\"TESTE\"\n" + 
				"}"
				, messageHeaders));
		return "Chibana's integration\n";
	}
	@GetMapping("channel2/{ID}")
	public String channel2(@PathVariable("ID") String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("ID", id);
		MessageHeaders messageHeaders = new MutableMessageHeaders(map);
		channel2.send(MessageBuilder.createMessage("teste", messageHeaders));		
		return "Chibana's integration\n";
	}
	
	@PostMapping("httpservice")
	public String channel3(@RequestBody String message) {
		return "HttpResult:200";
	}

}
