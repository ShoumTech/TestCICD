package com.ability.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OneTimePassAuthRequest {

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("one_time_passcode")
    private String oneTimePasscode;
}