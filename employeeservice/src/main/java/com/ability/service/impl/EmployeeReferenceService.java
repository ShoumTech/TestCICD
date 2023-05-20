package com.ability.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ability.entity.EmployeeEntity;
import com.ability.entity.ReferenceEntity;
import com.ability.exception.ProcessingException;
import com.ability.mapper.EntityToObjectMapper;
import com.ability.mapper.ObjectToEntityMapper;
import com.ability.model.QueryFieldsEnum;
import com.ability.model.Reference;
import com.ability.repository.EmployeeReferenceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeReferenceService {

    @Autowired
    private EmployeeProfileService employeeService;
    
    @Autowired
    private EmployeeReferenceRepository empReferenceRepository;
    
    public Reference createExployeeReference(long employeeId, Reference reference) throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        try {
            ReferenceEntity referenceEntity = populateReferenceEntity(reference, currentTime);
            if (Objects.nonNull(referenceEntity)) {
                referenceEntity.setActive('Y');
                EmployeeEntity employeeEntity = employeeService.getEmployeeEntity(employeeId);
                referenceEntity.setEmployeeEntity(employeeEntity);
                empReferenceRepository.save(referenceEntity);
                employeeService.updateActiveIndex(QueryFieldsEnum.REFERENCE.name(), employeeEntity);
                reference.setReferenceId(referenceEntity.getReferenceId().intValue());
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return reference;
    }
    
    public List<Reference> getEmployeeReferences(String employeeId) throws ProcessingException {
        List<Reference> referenceList = new ArrayList<>();
        try {
            if (StringUtils.isNoneEmpty(employeeId) && StringUtils.isNumeric(employeeId)) {
                List<ReferenceEntity> referenceEntityList = empReferenceRepository.findByEmployeeId(Long.parseLong(employeeId));
                if (CollectionUtils.isNotEmpty(referenceEntityList)) {

                    for (ReferenceEntity expEntity : referenceEntityList) {
                        Reference reference = EntityToObjectMapper.INSTANCE.mapToEmployeeReferencce(expEntity);
                        referenceList.add(reference);
                    }
                }
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return referenceList;
    }

   
    
    public int deleteEmployeeReference(long employeeId, long referenceId) throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        int count =0;
        try {
            if(referenceId != 0) {
                ReferenceEntity referenceEntity =  empReferenceRepository.findByReferenceIdAndEmployeeId(referenceId, employeeId);
                referenceEntity.setActive('N');
                referenceEntity.setTimeUpdated(currentTime);
                empReferenceRepository.save(referenceEntity);
                count++; 
            }
            else {
                List<ReferenceEntity> referenceEntityList = empReferenceRepository.findByEmployeeId(employeeId);
                if (CollectionUtils.isNotEmpty(referenceEntityList)) {
                    for (ReferenceEntity referenceEntity : referenceEntityList) {
                        referenceEntity.setActive('N');
                        referenceEntity.setTimeUpdated(currentTime);
                        empReferenceRepository.save(referenceEntity);
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return count;
    }
    
    private ReferenceEntity populateReferenceEntity(Reference reference, long currentTime) {
        if (Objects.isNull(reference)) {
            return null;
        }
        ReferenceEntity referenceEntity = ObjectToEntityMapper.INSTANCE.mapToReferenceEntity(reference);
        if(referenceEntity != null && referenceEntity.getReferenceId() == null) {
            referenceEntity.setTimeCreated(currentTime);
        }
        referenceEntity.setTimeUpdated(currentTime);
        return referenceEntity;
    }
}
