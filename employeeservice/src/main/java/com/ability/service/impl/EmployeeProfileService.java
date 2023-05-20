package com.ability.service.impl;

import java.time.Instant;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ability.entity.AddressEntity;
import com.ability.entity.EmployeeEntity;
import com.ability.exception.ProcessingException;
import com.ability.mapper.EntityToObjectMapper;
import com.ability.mapper.ObjectToEntityMapper;
import com.ability.model.Address;
import com.ability.model.Employee;
import com.ability.model.PersonalInfo;
import com.ability.model.QueryFieldsEnum;
import com.ability.model.Employee.IsConsentSignedEnum;
import com.ability.model.Employee.IsVisitedEnum;
import com.ability.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeProfileService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeEntity getEmployeeEntity(Long employeeId) throws ProcessingException {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId);
        if (Objects.isNull(employeeEntity)) {
            throw new ProcessingException("INVALID_REQUEST", "UNABLE_TO_LOAD_EMPLOYEE_DETAIL");
        }
        return employeeEntity;
    }
    
    public PersonalInfo createPersonalInfo(Long employeeId, PersonalInfo personalInfo) throws ProcessingException {
        try {
            long currentTime = Instant.now().getEpochSecond();
            if (Objects.isNull(personalInfo)) {
                return null;
            }
            EmployeeEntity employeeEntity = getEmployeeEntity(employeeId);
            populateEmployeeEntity(personalInfo, currentTime, employeeEntity);
            employeeRepository.save(employeeEntity);
            updateActiveIndex(QueryFieldsEnum.PROFILE.name(), employeeEntity);
            personalInfo.setEmployeeId(employeeEntity.getEmployeeId().intValue());
            if (employeeEntity.getAddressEntity() != null && employeeEntity.getAddressEntity().getAddressId() != null) {
                personalInfo.getAddress().setAddressId(employeeEntity.getAddressEntity().getAddressId().intValue());
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return personalInfo;
    }
    
    public EmployeeEntity populateEmployeeEntity(PersonalInfo personalInfo, long currentTime, EmployeeEntity employeeEntity) {
        if (Objects.isNull(personalInfo)) {
            return null;
        }
        ObjectToEntityMapper.INSTANCE.mapPersonalInfoToEmployeeEntity(employeeEntity, personalInfo);
        employeeEntity.setTimeUpdated(currentTime);
        employeeEntity.setAddressEntity(populateAddressEntity(personalInfo.getAddress(), currentTime));
        return employeeEntity;

    }
    private AddressEntity populateAddressEntity(Address address, long currentTime) {
        if (Objects.isNull(address)) {
            return null;
        }
        AddressEntity addressEntity = ObjectToEntityMapper.INSTANCE.mapAddressToAddressEntity(address);
        if(addressEntity != null && addressEntity.getAddressId() == null) {
            addressEntity.setTimeCreated(currentTime);
        }
        addressEntity.setTimeUpdated(currentTime);
        return addressEntity;
    }

    public boolean updateActiveIndex(String activeIndex, EmployeeEntity empEntity) {
        try {
        empEntity.setActiveTabIndex(activeIndex);
        employeeRepository.save(empEntity);
        return true;
        }catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    public PersonalInfo getPersonalInfo(String employeeId) throws ProcessingException {
        return getPersonalInfo(employeeId, null);
    }

    public PersonalInfo getPersonalInfo(String employeeId, Employee emp) throws ProcessingException {
        PersonalInfo personalInfo = new PersonalInfo();
        try {
            if (StringUtils.isNoneEmpty(employeeId) && StringUtils.isNumeric(employeeId)) {
                EmployeeEntity empEntity = employeeRepository.findByEmployeeId(Long.parseLong(employeeId));

                if (Objects.nonNull(empEntity)) {
                    personalInfo = EntityToObjectMapper.INSTANCE.mapEmployeeEntityToPersonalInfo(empEntity);
                    if (Objects.nonNull(empEntity.getAddressEntity())) {
                        Address address = EntityToObjectMapper.INSTANCE
                                .mapAddressEntityToAddress(empEntity.getAddressEntity());
                        personalInfo.setAddress(address);
                    }
                    if(Objects.nonNull(emp) && empEntity.getIsVisited() != null) {
                        emp.setIsVisited(IsVisitedEnum.valueOf(empEntity.getIsVisited()));
                    }
                    if(Objects.nonNull(emp) && empEntity.getIsConsentSigned() != null) {
                        emp.setIsConsentSigned(IsConsentSignedEnum.valueOf(empEntity.getIsConsentSigned()));
                    }
                    if(Objects.nonNull(emp) && empEntity.getActiveTabIndex() != null) {
                        emp.setActiveTabIndex(empEntity.getActiveTabIndex());
                    }
                }
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return personalInfo;
    }

}
