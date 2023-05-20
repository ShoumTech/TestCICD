package com.ability.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ability.entity.ExperienceEntity;

public interface ExperienceRepository extends JpaRepository<ExperienceEntity, Long> {

    @Query(value = "SELECT * FROM EXPERIENCE e WHERE e.EMPLOYEE_ID = :employeeId AND e.ACTIVE='Y'", nativeQuery = true)
    public List<ExperienceEntity> findByEmployeeId(Long employeeId);

    public ExperienceEntity findByExperienceId(Long experienceId);
    
    @Query(value = "SELECT * FROM EXPERIENCE e WHERE  e.EXPERIENCE_ID=:experienceId AND e.EMPLOYEE_ID = :employeeId", nativeQuery = true)
    public ExperienceEntity findByExperienceIdAndEmployeeId(Long experienceId, Long employeeId);
}
