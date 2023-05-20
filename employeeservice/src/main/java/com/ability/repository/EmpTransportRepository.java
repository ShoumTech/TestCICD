package com.ability.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ability.entity.TransportEntity;

public interface EmpTransportRepository extends JpaRepository<TransportEntity, Long> {

    @Query(value = "SELECT * FROM EMP_TRANSPORT e WHERE e.EMPLOYEE_ID = :employeeId", nativeQuery = true)
    public List<TransportEntity> findByEmployeeId(Long employeeId);

    public TransportEntity findByTransportId(Long transportId);

}
