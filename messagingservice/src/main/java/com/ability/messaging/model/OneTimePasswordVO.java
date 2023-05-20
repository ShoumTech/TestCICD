package com.ability.messaging.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneTimePasswordVO {

    @NotEmpty
    private String emailAddress;

    @NotEmpty
    private String emailSubject;

    @NotEmpty
    private String emailMessage;

}
