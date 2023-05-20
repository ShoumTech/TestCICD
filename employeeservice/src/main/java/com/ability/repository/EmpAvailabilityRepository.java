package com.ability.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ability.entity.EmployeeAvailabilityEntity;

public interface EmpAvailabilityRepository extends JpaRepository<EmployeeAvailabilityEntity, Long> {

    @Query(value = "SELECT * FROM EMP_AVALIABILITY e WHERE e.EMPLOYEE_ID = :employeeId", nativeQuery = true)
    public List<EmployeeAvailabilityEntity> findByEmployeeId(Long employeeId);

    public EmployeeAvailabilityEntity findByAvailabilityId(Long availabilityId);
}
