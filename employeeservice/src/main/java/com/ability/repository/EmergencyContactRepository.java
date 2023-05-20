package com.ability.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ability.entity.EmergencyContactEntity;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContactEntity, Long> {

    @Query(value = "SELECT * FROM EMERGENCY_CONTACT e WHERE e.EMPLOYEE_ID = :employeeId", nativeQuery = true)
    public List<EmergencyContactEntity> findByEmployeeId(Long employeeId);

    public EmergencyContactEntity findByEmergencyContactId(Long emergencyContactId);
}
