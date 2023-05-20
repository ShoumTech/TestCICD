package com.ability.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ONE_TIME_PASS_CODE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OneTimePassCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ONE_TIME_PASS_CODE_ID", unique = true)
    private Long oneTimePassCodeId;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "OTP_CODE")
    private String otpCode;

    @Column(name = "EXPIRARY_TIME")
    private Long expiraryTime;

    @Column(name = "GENERATED_TIME")
    private Long generatedTime;

    @Column(name = "IS_EXPIRED")
    private String isExpired;
}
