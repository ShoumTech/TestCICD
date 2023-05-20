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
import java.util.List;

import com.ability.model.Employee.IsConsentSignedEnum;
import com.ability.model.Employee.IsVisitedEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
public class PersonalInfo {

    @JsonProperty("employee_id")
    private Integer employeeId = null;

    @JsonProperty("email_address")
    private String emailAddress = null;

    @JsonProperty("name")
    private Name name = null;

    /**
     * Gets or Sets gender
     */
    public enum GenderEnum {
        MALE("MALE"), FEMALE("FEMALE"), OTHERS("OTHERS");

        private String value;

        GenderEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static GenderEnum fromValue(String input) {
            for (GenderEnum b : GenderEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }

    }

    @JsonProperty("gender")
    private GenderEnum gender = null;

    @JsonProperty("date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth = null;

    @JsonProperty("phone_number")
    private Phone phoneNumber = null;

    @JsonProperty("work_phone_number")
    private Phone workPhoneNumber = null;

    @JsonProperty("document")
    private Document document = null;

    @JsonProperty("address")
    private Address address = null;

    @JsonProperty("how_did_you_hear_about_us")
    private String howDidYouHearAboutUs = null;

    @JsonProperty("most_like_about_working_with_disabilities")
    private String mostLikeAboutWorkingWithDisabilities = null;

    @JsonProperty("most_challenge_in_work")
    private String mostChallengeInWork = null;

    @JsonProperty("have_you_convicted_crime")
    private String haveYouConvictedCrime = null;

    @JsonProperty("crime_reason")
    private String crimeReason = null;

    @JsonProperty("position_apply_for")
    private String positionApplyFor = null;

    @JsonProperty("have_mental_physical_illness")
    private String haveMentalPhysicalIllness = null;

    @JsonProperty("resume_uploaded_path")
    private String resumeUploadedPath = null;

}