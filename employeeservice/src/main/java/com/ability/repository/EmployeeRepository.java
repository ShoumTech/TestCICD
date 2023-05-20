package com.ability.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ability.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    public EmployeeEntity findByEmployeeId(Long employeeId);

    @Query(value = "SELECT * FROM EMPLOYEE e WHERE e.EMAIL_ADDRESS = :emailAddress ORDER BY TIME_UPDATED DESC LIMIT 1" , nativeQuery = true)
    public EmployeeEntity findByEmailAddress(String emailAddress);
   
    public Page<EmployeeEntity> findAll(Specification<EmployeeEntity> spec, Pageable pageable);
    
    public List<EmployeeEntity> findAll(Specification<EmployeeEntity> spec);



}
