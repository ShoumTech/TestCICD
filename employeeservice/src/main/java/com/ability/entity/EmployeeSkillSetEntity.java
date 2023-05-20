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
@Table(name = "EMPLOYEE_SKILLSET")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EmployeeSkillSetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "SKILLSET_ID", unique = true)
    private Long skillsetId;

    @Column(name = "SKILLSET_GROUP", length = 128)
    private String skillsetGroup;

    @Column(name = "SKILLSET_NAME", length = 128)
    private String skillsetName;

    @Column(name = "SKILLSET_VALUE", length = 36)
    private String skillsetValue;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private EmployeeEntity employeeEntity;
}
