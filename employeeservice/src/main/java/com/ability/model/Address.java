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
public class Address {

    @JsonProperty("address_id")
    private Integer addressId = null;

    @JsonProperty("address_line_1")
    private String addressLine1 = null;

    @JsonProperty("address_line_2")
    private String addressLine2 = null;

    @JsonProperty("city")
    private String city = null;

    @JsonProperty("state")
    private String state = null;

    @JsonProperty("postal_code")
    private Integer postalCode = null;

    @JsonProperty("time_created")
    private String timeCreated = null;

    @JsonProperty("time_updated")
    private String timeUpdated = null;

    @JsonProperty("living_since_in_years")
    private String livingSinceInYears = null;

}