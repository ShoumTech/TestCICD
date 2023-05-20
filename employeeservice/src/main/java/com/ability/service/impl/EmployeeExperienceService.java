package com.ability.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ability.entity.EmployeeEntity;
import com.ability.entity.ExperienceEntity;
import com.ability.exception.ProcessingException;
import com.ability.mapper.EntityToObjectMapper;
import com.ability.mapper.ObjectToEntityMapper;
import com.ability.model.Experience;
import com.ability.model.QueryFieldsEnum;
import com.ability.repository.ExperienceRepository;
import com.ability.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeExperienceService {

    @Autowired
    private EmployeeProfileService employeeService;

    @Autowired
    private ExperienceRepository experienceRepository;

    public Experience createExployeeExperience(long employeeId, Experience experience) throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        try {
            ExperienceEntity experienceEntity = populateExperienceEntity(experience, currentTime);
            if (Objects.nonNull(experienceEntity)) {
                experienceEntity.setActive('Y');
                EmployeeEntity employeeEntity = employeeService.getEmployeeEntity(employeeId);
                experienceEntity.setEmployeeEntity(employeeEntity);
                experienceRepository.save(experienceEntity);
                employeeService.updateActiveIndex(QueryFieldsEnum.EXPERIENCE.name(), employeeEntity);
                experience.setExperienceId(experienceEntity.getExperienceId().intValue());
            }
        } catch (Exception e) {
            log.error("Exception::EmployeeExperienceService::createExployeeExperience::"+ExceptionUtils.getStackTrace(e));
            throw new ProcessingException(e);
        }
        return experience;
    }

    private ExperienceEntity populateExperienceEntity(Experience experience, long currentTime) {
        if (Objects.isNull(experience)) {
            return null;
        }
        ExperienceEntity experienceEntity = ObjectToEntityMapper.INSTANCE.mapToExperienceEntity(experience);
        if (experienceEntity != null && experienceEntity.getExperienceId() == null) {
            experienceEntity.setTimeCreated(currentTime);
        }
        experienceEntity.setTimeUpdated(currentTime);

        Date startDate = DateUtils.asDate(experience.getStartDate());
        Date endDate = DateUtils.asDate(experience.getEndDate());
        experienceEntity.setStartDate(startDate);
        experienceEntity.setEndDate(endDate);
        return experienceEntity;
    }

    public List<Experience> getEmployeeExperiences(String employeeId) throws ProcessingException {
        List<Experience> experienceList = new ArrayList<>();
        try {
            if (StringUtils.isNoneEmpty(employeeId) && StringUtils.isNumeric(employeeId)) {
                List<ExperienceEntity> expList = experienceRepository.findByEmployeeId(Long.parseLong(employeeId));
                if (CollectionUtils.isNotEmpty(expList)) {

                    for (ExperienceEntity expEntity : expList) {
                        Experience education = EntityToObjectMapper.INSTANCE.mapToEmployeeExperience(expEntity);
                        experienceList.add(education);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception::EmployeeExperienceService::getEmployeeExperiences::"+ExceptionUtils.getStackTrace(e));
            throw new ProcessingException(e);
        }
        return experienceList;
    }

    public int deleteEmployeeExperience(long employeeId, long experienceId) throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        int count = 0;
        try {
            if (experienceId != 0) {
                ExperienceEntity experienceEntity = experienceRepository.findByExperienceIdAndEmployeeId(experienceId,
                        employeeId);
                experienceEntity.setActive('N');
                experienceEntity.setTimeUpdated(currentTime);
                experienceRepository.save(experienceEntity);
                count++;
            } else {
                List<ExperienceEntity> expList = experienceRepository.findByEmployeeId(employeeId);
                if (CollectionUtils.isNotEmpty(expList)) {
                    for (ExperienceEntity expEntity : expList) {
                        expEntity.setActive('N');
                        expEntity.setTimeUpdated(currentTime);
                        experienceRepository.save(expEntity);
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception::EmployeeExperienceService::deleteEmployeeExperience::"+ExceptionUtils.getStackTrace(e));
            throw new ProcessingException(e);
        }
        return count;
    }

}
