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
@Table(name = "EXPERIENCE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXPERIENCE_ID", unique = true)
    private Long experienceId;

    @Column(name = "COMPANY", length = 128)
    private String company;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "REASON_LEFT", length = 60)
    private String reasonLeft;

    @Column(name = "IS_CURRENT", length = 12)
    private Boolean isCurrentCompany;

    @Column(name = "MANAGER_NAME", length = 128)
    private String managerName;

    @Column(name = "PHONE_NUMBER", length = 24)
    private String phoneNumber;

    @Column(name = "JOB_TITLE", length = 128)
    private String jobTitle;

    @Column(name = "CAN_WE_CONTACT", length = 12)
    private Boolean canWeContact;

    @Column(name = "TIME_CREATED")
    private Long timeCreated;

    @Column(name = "TIME_UPDATED")
    private Long timeUpdated;
    
    @Column(name = "ACTIVE")
    private Character active;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private EmployeeEntity employeeEntity;

}
