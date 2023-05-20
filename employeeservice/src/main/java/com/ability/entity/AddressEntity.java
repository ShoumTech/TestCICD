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
@Table(name = "ADDRESS")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID", unique = true)
    private Integer addressId;

    @Column(name = "ADDRESS_LINE_1", length = 128)
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2", length = 128)
    private String addressLine2;

    @Column(name = "CITY", length = 128)
    private String city;

    @Column(name = "STATE", length = 48)
    private String state;

    @Column(name = "POSTAL_CODE")
    private Integer postalCode;

    @Column(name = "LIVING_SINCE_YRS")
    private String livingSinceInYears;

    @Column(name = "TIME_CREATED")
    private Long timeCreated;

    @Column(name = "TIME_UPDATED")
    private Long timeUpdated;

}