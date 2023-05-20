package com.ability.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID", unique = true)
    private Long employeeId;

    @Column(name = "EMAIL_ADDRESS", length = 128)
    private String emailAddress;

    @Column(name = "FIRST_NAME", length = 128)
    private String firstName;

    @Column(name = "MIDDLE_NAME", length = 128)
    private String middleName;

    @Column(name = "LAST_NAME", length = 128)
    private String lastName;

    @Column(name = "GENDER", length = 36)
    private String gender;

    @Column(name = "PHONE_NUMBER", length = 24)
    private String phoneNumber;

    @Column(name = "WORK_PHONE_NUMBER", length = 24)
    private String workPhoneNumber;

    @Column(name = "HOW_DID_YOU_HEAR_ABOUTUS", length = 48)
    private String howDidYouHearAboutUs;

    @Column(name = "MOST_LIKE_IN_WORK", length = 60)
    private String mostLikeAboutWorkingWithDisabilities;

    @Column(name = "MOST_CHALLENGE_IN_WORK", length = 60)
    private String mostChallengeInWork;

    @Column(name = "HAVE_YOU_CONVICTED_CRIME", length = 36)
    private String haveYouConvictedCrime;
    
    @Column(name = "CRIME_REASON", length = 256)
    private String crimeReason;

    @Column(name = "POSITION_APPLY_FOR", length = 36)
    private String positionApplyFor;

    @Column(name = "HAVE_MENTAL_ILLNESS", length = 36)
    private String haveMentalPhysicalIllness;

    @Column(name = "RESUME_UPLOADED_PATH", length = 128)
    private String resumeUploadedPath;

    @Column(name = "SSN", length = 24)
    private String SSN;

    @Column(name = "CONFIRM_SSN", length = 24)
    private String confirmSSN;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    private AddressEntity addressEntity;

    @Column(name = "TIME_CREATED")
    private Long timeCreated;

    @Column(name = "TIME_UPDATED")
    private Long timeUpdated;

    @Column(name = "IS_VISITED")
    private String isVisited;
    
    @Column(name="IS_CONSENT_SIGNED")
    private String isConsentSigned;
    
    @Column(name = "DOB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    
    @Column(name ="ACTIVE_TAB_INDEX")
    private String activeTabIndex;
    
    @Column(name ="SIGNATURE")
    private String signature;

}
