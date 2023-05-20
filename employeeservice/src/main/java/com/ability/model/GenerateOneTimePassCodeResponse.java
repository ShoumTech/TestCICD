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
public class GenerateOneTimePassCodeResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("onetime_pass_code")
    private String passCode;
    
    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("employee_id")
    private String employeeId;

}
