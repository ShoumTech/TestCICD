package com.ability.messaging.api;

import javax.validation.Valid;

import com.ability.messaging.model.OneTimePasswordVO;


public interface EmailService {

	OneTimePasswordVO makeOneTimePasswordVO(String emailAddress,  String tokenString);

	void sendSimpleMessage(@Valid OneTimePasswordVO vo);

 
}
