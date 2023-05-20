package com.ability.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ErrorMessage {

    private String errorType;

    private String messageCode;

    private String stackTrace;
}
