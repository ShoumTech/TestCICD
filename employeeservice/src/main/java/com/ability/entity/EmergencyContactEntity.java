package com.ability.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "EMERGENCY_CONTACT")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EmergencyContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMERGENCY_CONTACT_ID", unique = true)
    private Integer emergencyContactId;

    @Column(name = "FIRST_NAME", length = 128)
    private String firstName;

    @Column(name = "MIDDLE_NAME", length = 128)
    private String middleName;

    @Column(name = "LAST_NAME", length = 128)
    private String lastName;

    @Column(name = "PHONE_NUMBER", length = 24)
    private String phoneNumber;

    @Column(name = "ALT_PHONE_NUMBER", length = 24)
    private String altPhoneNumber;

    @Column(name = "RELATIONSHIP", length = 36)
    private String relationship;

    @Column(name = "TIME_CREATED")
    private Long timeCreated;

    @Column(name = "TIME_UPDATED")
    private Long timeUpdated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    private AddressEntity addressEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private EmployeeEntity employeeEntity;

}
