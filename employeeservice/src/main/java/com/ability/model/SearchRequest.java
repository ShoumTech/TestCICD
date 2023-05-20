package com.ability.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(Include. NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SearchRequest {

    private String isVisited;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private Phone phoneNumber;
    private String emailAddress;
    private Integer pageNumber;
    private Integer pageSize;
    private String isConsentSigned;

}
