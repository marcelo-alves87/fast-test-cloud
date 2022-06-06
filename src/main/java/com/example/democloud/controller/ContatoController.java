package com.example.democloud.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.democloud.domain.Contato;
import com.example.democloud.repository.ContatoRepository;

@RestController
public class ContatoController {

	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${cloud.aws.end-point.uri}")
	private String endPoint;

	@PostMapping("/send/{message}")
	ResponseEntity<String> sendMessageToQueue(@PathVariable String message) {
		queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(message).build());
		return ResponseEntity.ok("200");
	}

	@GetMapping(path = "/test")
	ResponseEntity<String> test() {
		return ResponseEntity.ok("200");
	}

	@PostMapping(path = "/insert")
	ResponseEntity<String> insert(@Valid @RequestBody Contato contato) {
		this.contatoRepository.save(contato);
		queueMessagingTemplate.send(endPoint,
				MessageBuilder.withPayload("{ \"email\" : \"" + contato.getEmail() + "\" }").build());
		return ResponseEntity.ok("200");
	}
}
