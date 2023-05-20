package com.ability.messaging.impl;

import javax.validation.Valid;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ability.messaging.api.EmailService;
import com.ability.messaging.model.OneTimePasswordVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
public class EmailServiceImpl implements EmailService {
    private static final String NO_REPLY_ADDRESS = "info@solucreed.com";


    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public OneTimePasswordVO makeOneTimePasswordVO(String emailAddress, String emailMsg) {
        return OneTimePasswordVO.builder()
                .emailAddress(emailAddress)
                .emailMessage(emailMsg)
                .emailSubject("One-Time-Password")
                .build();
    }
    @Override
    public void sendSimpleMessage(@Valid OneTimePasswordVO oneTimePasswordVO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NO_REPLY_ADDRESS);
            message.setTo(oneTimePasswordVO.getEmailAddress());
            message.setSubject(oneTimePasswordVO.getEmailSubject());
            message.setText(oneTimePasswordVO.getEmailMessage());

            emailSender.send(message);
        } catch (MailException e) {
            log.error(e.getMessage());
        }
    }
}
