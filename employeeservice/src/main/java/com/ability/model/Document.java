/*
 * Ability Options LLC App
 * This servers hosts API needed for Ability Options LLC.
 *
 * OpenAPI spec version: 1.0.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.ability.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@JsonInclude(Include. NON_NULL)
public class Document {

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("value")
    private String value = null;

    @JsonProperty("confirm_value")
    private String confirmValue = null;

    @JsonProperty("expiration")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiration = null;

}