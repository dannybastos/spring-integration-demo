package com.spring.integration.sample.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.integration.sample.model.HelloModel;

@RestController
public class SampleController {
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("inputChannel1")
	private MessageChannel channel1;
	@Autowired
	@Qualifier("inputChannel2")
	private MessageChannel channel2;

    @Autowired
    @Qualifier("httpChannel")
    private MessageChannel httpChannel;

    @Autowired
    @Qualifier("atomicId")
    private AtomicInteger atomicId;
	
    /**
     * This method receive the msg, create a HelloModel and send it as a message
     * into channel1
     * @param msg
     * @return
     */
	@GetMapping("channel1/{msg}")
	public ResponseEntity<Serializable> channel1(@PathVariable("msg") String msg) {
		channel1.send(new GenericMessage<>(this.createMsg(msg)));
		return ResponseEntity.ok("Chibana's integration\n");
	}
	
	/**
     * This method receive the msg, create a HelloModel and send it as a message
     * into channel2
	 * @param msg
	 * @return
	 */
	@GetMapping("channel2/{msg}")
	public ResponseEntity<Serializable> channel2(@PathVariable("msg") String msg) {
		channel2.send(new GenericMessage<>(this.createMsg(msg)));
		return ResponseEntity.ok("Chibana's integration\n");
	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	@GetMapping("http/{msg}")
	public ResponseEntity<Serializable> start(@PathVariable("msg") String msg) {
        MessagingTemplate messagingTemplate = new MessagingTemplate(httpChannel);
        messagingTemplate.setReceiveTimeout(1000);
        Optional<?> resp;
        try {
        	resp = Optional.ofNullable(messagingTemplate.sendAndReceive(this.createMsg(msg)));
        	if (resp.isPresent())
        		return ResponseEntity.ok(resp.get().toString());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(((MessageHandlingException)e).getFailedMessage().getPayload().toString());
		}
        return ResponseEntity.ok(resp.get().toString());
	}

    private Message<Serializable> createMsg(String msg) {
        Map<String, Object> map = new HashMap<>();
        final int id = atomicId.getAndIncrement();
        map.put("ID", id);
        MessageHeaders messageHeaders = new MessageHeaders(map);
        HelloModel model = new HelloModel(msg, id);
        String json = null;
        try {
			json = new ObjectMapper().writeValueAsString(model);
		} catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
		}
        return MessageBuilder.createMessage(json, messageHeaders);
    }

	
}
