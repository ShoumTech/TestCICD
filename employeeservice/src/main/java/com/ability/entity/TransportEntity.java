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
@Table(name = "EMP_TRANSPORT")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class TransportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSPORT_ID", unique = true)
    private Long transportId;

    @Column(name = "HAVE_A_CAR", length = 12)
    private String haveACar;

    @Column(name = "ALT_MODE_OF_TRANSPORT", length = 48)
    private String altModeOfTransport;

    @Column(name = "LIENSE_NUMBER", length = 36)
    private String lienseNumber;

    @Column(name = "LIENSE_EXP_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lienseExpirationDate;

    @Column(name = "TIME_CREATED")
    private Long timeCreated;

    @Column(name = "TIME_UPDATED")
    private Long timeUpdated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private EmployeeEntity employeeEntity;
}
