package com.ability.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ability.entity.ReferenceEntity;

public interface EmployeeReferenceRepository  extends JpaRepository<ReferenceEntity, Long> {


    @Query(value = "SELECT * FROM REFERENCE e WHERE e.EMPLOYEE_ID = :employeeId AND e.ACTIVE='Y'", nativeQuery = true)
    public List<ReferenceEntity> findByEmployeeId(Long employeeId);

    public ReferenceEntity findByReferenceId(Long referenceId);
    
    @Query(value = "SELECT * FROM REFERENCE e WHERE  e.REFERENCE_ID=:referenceId AND e.EMPLOYEE_ID = :employeeId", nativeQuery = true)
    public ReferenceEntity findByReferenceIdAndEmployeeId(Long referenceId, Long employeeId);
}
