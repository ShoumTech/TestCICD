package com.ability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ability.entity.OneTimePassCodeEntity;

public interface OneTimePasscodeRepository extends JpaRepository<OneTimePassCodeEntity, Long> {

    @Query(value = "SELECT * FROM ONE_TIME_PASS_CODE o WHERE o.EMPLOYEE_ID = :employeeId" , nativeQuery = true)
    public OneTimePassCodeEntity findbyEmployeeId(Long employeeId);

}
