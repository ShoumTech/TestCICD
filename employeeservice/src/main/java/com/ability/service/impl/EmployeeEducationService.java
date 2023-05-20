package com.ability.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ability.entity.EducationEntity;
import com.ability.entity.EmployeeEntity;
import com.ability.exception.ProcessingException;
import com.ability.mapper.EntityToObjectMapper;
import com.ability.mapper.ObjectToEntityMapper;
import com.ability.model.Education;
import com.ability.model.QueryFieldsEnum;
import com.ability.repository.EducationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeEducationService {


    @Autowired
    private EducationRepository educationRepository;
    
    @Autowired
    private EmployeeProfileService employeeService;
    
    public Education createEmployeeEducation(long employeeId, Education education) throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        try {
            EducationEntity educationEntity = populateEducationEntity(education, currentTime);
            if (Objects.nonNull(educationEntity)) {
                educationEntity.setActive('Y');
                EmployeeEntity employeeEntity = employeeService.getEmployeeEntity(employeeId);
                educationEntity.setEmployeeEntity(employeeEntity);
                educationRepository.save(educationEntity);
                employeeService.updateActiveIndex(QueryFieldsEnum.EDUCATION.name(), employeeEntity);
                education.setEducationId(educationEntity.getEducationId().intValue());
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return education;
    }
    
    public List<Education> getEmployeeEducationList(String employeeId) throws ProcessingException {
        List<Education> educationList = new ArrayList<>();
        try {
            if (StringUtils.isNoneEmpty(employeeId) && StringUtils.isNumeric(employeeId)) {
                List<EducationEntity> eduEntityList = educationRepository.findByEmployeeId(Long.parseLong(employeeId));
                if (CollectionUtils.isNotEmpty(eduEntityList)) {

                    for (EducationEntity eduEntity : eduEntityList) {
                        Education education = EntityToObjectMapper.INSTANCE.mapToEmployeeEducation(eduEntity);
                        educationList.add(education);
                    }
                }
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return educationList;
    }
    
    public int deleteEmployeeEducation(long employeeId, long educationId) throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        int count =0;
        try {
            if(educationId != 0) {
                EducationEntity educationEntity =  educationRepository.findByEducationIdAndEmployeeId(educationId, employeeId);
                educationEntity.setActive('N');
                educationEntity.setTimeUpdated(currentTime);
                educationRepository.save(educationEntity);
                count++; 
            }
            else {
                List<EducationEntity> educationList = educationRepository.findByEmployeeId(employeeId);
                if (CollectionUtils.isNotEmpty(educationList)) {
                    for (EducationEntity expEntity : educationList) {
                        expEntity.setActive('N');
                        expEntity.setTimeUpdated(currentTime);
                        educationRepository.save(expEntity);
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return count;
    }

    private EducationEntity populateEducationEntity(Education education, Long currentTime) {
        if (Objects.isNull(education)) {
            return null;
        }
        EducationEntity educationEntity = ObjectToEntityMapper.INSTANCE.mapToEducationEntity(education);
        if(educationEntity != null && educationEntity.getEducationId() == null) {
            educationEntity.setTimeCreated(currentTime);
        }
        educationEntity.setTimeUpdated(currentTime);
        return educationEntity;
    }
}
