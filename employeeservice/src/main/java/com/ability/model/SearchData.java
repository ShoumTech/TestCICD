package com.ability.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class SearchData {

    private String isVisited;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private Phone phoneNumber;
    private String emailAddress;
    private String gender;
    private Long employeeId;
    private String isConsentSigned;
}
