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
@Table(name = "EMP_AVALIABILITY")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EmployeeAvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AVAILABILITY_ID", unique = true)
    private Long availabilityId;

    @Column(name = "WORK_HOURS_PER_WEEK", length = 36)
    private String workHoursPerWeek;

    @Column(name = "WORK_ON_WEEKENDS", length = 36)
    private String workOnWeekends;

    @Column(name = "WORK_ON_NIGHTS", length = 36)
    private String workOnNights;

    @Column(name = "AUTHORIZED_TO_WORK", length = 48)
    private String legallyAuthorizedToWork;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AVAIL_START_DATE")
    private Date availableStartDate;

    @Column(name = "TIMES_NOT_AVAIL_TO_WORK", length = 60)
    private String timesWhemNotAvailableWork;

    @Column(name = "EMPLOYMENT_DESIRED", length = 60)
    private String employmentDesired;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private EmployeeEntity employeeEntity;
    
    @Column(name = "TIME_CREATED")
    private Long timeCreated;

    @Column(name = "TIME_UPDATED")
    private Long timeUpdated;
}
