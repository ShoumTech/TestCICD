package com.ability.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ability.entity.EducationEntity;
import com.ability.entity.EmployeeEntity;

public interface EducationRepository extends JpaRepository<EducationEntity, Long> {

    public List<EducationEntity> findByEmployeeEntity(EmployeeEntity employeeEntity);

    @Query(value = "SELECT * FROM EDUCATION e WHERE e.EMPLOYEE_ID = :employeeId AND e.ACTIVE='Y'", nativeQuery = true)
    public List<EducationEntity> findByEmployeeId(Long employeeId);
    
    public EducationEntity findByEducationId(Long educationId);
    
    @Query(value = "SELECT * FROM EDUCATION e WHERE  e.EDUCATION_ID=:educationId AND e.EMPLOYEE_ID = :employeeId", nativeQuery = true)
    public EducationEntity findByEducationIdAndEmployeeId(Long educationId, Long employeeId);


}
