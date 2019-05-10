package com.spring.integration.sample.controller;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.integration.sample.model.Hello;

@RestController
@RequestMapping("channel")
public class SampleController {
	private static final int RESPONSE_TIMEOUT = 3000;
	private final MessageChannel channel1;
	private final MessageChannel channel2;
    private final MessageChannel httpChannel;
    private final ObjectMapper mapper;
    
	public SampleController(@Qualifier("inputChannel1") MessageChannel channel1
			, @Qualifier("inputChannel2") MessageChannel channel2
			, @Qualifier("httpChannel") MessageChannel httpChannel
			, @Qualifier("atomicId") AtomicInteger atomicId
			, ObjectMapper mapper) {
		super();
		this.channel1 = channel1;
		this.channel2 = channel2;
		this.httpChannel = httpChannel;
		this.mapper = mapper;
	}    
	
    /**
     * This method receive the msg, create a HelloModel and send it as a message
     * into channel1
     * @param msg
     * @return
     * @throws JsonProcessingException 
     */
	@GetMapping("1/{msg}")
	public ResponseEntity<Serializable> channel1(@PathVariable("msg") String msg) throws JsonProcessingException {
		ResponseEntity<Serializable> result;
        try {
        	String json = sendMessage(msg, channel1);
			result = ResponseEntity.ok(json);
		} catch (Exception e) {
			result = ResponseEntity.badRequest().body(((MessageHandlingException)e).getFailedMessage().getPayload().toString());
		}
        return result;
	}

	/**
     * This method receive the msg, create a HelloModel and send it as a message
     * into channel2
	 * @param msg
	 * @return
	 * @throws JsonProcessingException 
	 */
	@GetMapping("2/{msg}")
	public ResponseEntity<Serializable> channel2(@PathVariable("msg") String msg) throws JsonProcessingException {
		ResponseEntity<Serializable> result;
        try {
        	String json = sendMessage(msg, channel2);
			result = ResponseEntity.ok(json);
		} catch (Exception e) {
			result = ResponseEntity.badRequest().body(e.getMessage());
		}
        return result;
	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	@GetMapping("http/{msg}")
	public ResponseEntity<Serializable> start(@PathVariable("msg") String msg) {
		ResponseEntity<Serializable> result;
        try {
        	String json = sendMessage(msg, httpChannel);
			result = ResponseEntity.ok(json);
		} catch (Exception e) {
			result = ResponseEntity.badRequest().body(e.getMessage());
		}
        return result;
	}

	private String convertResponseToJSON(Message<?> responseMessage) throws JsonProcessingException {
		Hello hello = (Hello) responseMessage.getPayload();
		String json = mapper.writeValueAsString(hello);
		return json;
	}

	private String sendMessage(String msg, MessageChannel channel) throws JsonProcessingException {
		MessagingTemplate messagingTemplate = new MessagingTemplate(channel);
        messagingTemplate.setReceiveTimeout(RESPONSE_TIMEOUT);
        Hello hello = new Hello(msg, 0);
        String json = null;
        try {
        	Optional<?> responseMessage = Optional.ofNullable(messagingTemplate.sendAndReceive(
					MessageBuilder.withPayload(mapper.writeValueAsString(hello)).build()));
            if (responseMessage.isPresent()) {
            	Message<String> responseMessage2 = (Message<String>) responseMessage.get();
    			json = responseMessage2.getPayload();
            }
            else {
            	throw new RuntimeException(String.format("Failed to put message [%s] on channel [%s]",msg, channel)) ;
            }        	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        
		return json;
	}
}
