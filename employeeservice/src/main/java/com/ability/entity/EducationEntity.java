package com.ability.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "EDUCATION")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EducationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDUCATION_ID", unique = true)
    private Long educationId;

    @Column(name = "SCHOOL_TYPE", length = 36)
    private String schoolType;

    @Column(name = "SCHOOL_NAME", length = 128)
    private String name;

    @Column(name = "CITY", length = 128)
    private String city;

    @Column(name = "STATE", length = 48)
    private String state;

    @Column(name = "COUNTRY", length = 128)
    private String country;

    @Column(name = "LEVEL_COMPLETED", length = 128)
    private String levelCompleted;

    @Column(name = "DEGREE", length = 128)
    private String degree;

    @Column(name = "MAJOR", length = 128)
    private String major;

    @Column(name = "DEGREE_CERT_PATH", length = 128)
    private String degreeCertificatePath;

    @Column(name = "TIME_CREATED")
    private Long timeCreated;

    @Column(name = "TIME_UPDATED")
    private Long timeUpdated;
    
    @Column(name = "ACTIVE")
    private Character active;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private EmployeeEntity employeeEntity;
}
