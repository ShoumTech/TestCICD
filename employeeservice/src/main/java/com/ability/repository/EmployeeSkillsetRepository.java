package com.ability.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ability.entity.EmployeeSkillSetEntity;

public interface EmployeeSkillsetRepository extends JpaRepository<EmployeeSkillSetEntity, Long> {

    @Query(value = "SELECT * FROM EMPLOYEE_SKILLSET e WHERE e.EMPLOYEE_ID = :employeeId", nativeQuery = true)
    public List<EmployeeSkillSetEntity> findByEmployeeId(Long employeeId);

    public EmployeeSkillSetEntity findBySkillsetId(Long skillsetId);
}
