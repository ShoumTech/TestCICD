package com.ability.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class OneTimePassAuthResponse {
    
    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("is_onetime_passcode_valid")
    private boolean isOneTimePasscodeValid;
}
