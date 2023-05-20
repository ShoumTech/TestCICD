package com.ability.messaging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ability.messaging.api.EmailService;
import com.ability.messaging.model.OneTimePasswordVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api")
@Slf4j
public class EmailServiceRestController {
	
	@Autowired
	private EmailService emailService;

	    @RequestMapping(value = "/messaging/{emailAddress}/sendEmail/{tokenString}", produces = "application/json", method = RequestMethod.GET)
	    public ResponseEntity<String> sendTokenByEmail(@PathVariable String emailAddress, @PathVariable String tokenString) {
	        log.info("..... entering method ....");
	        OneTimePasswordVO vo = emailService.makeOneTimePasswordVO(emailAddress,tokenString);
	        emailService.sendSimpleMessage(vo);
	        return new ResponseEntity<>("successful",HttpStatus.OK);
	    }
}
