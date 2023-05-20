package com.ability.controller;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ability.exception.ProcessingException;
import com.ability.model.Experience;
import com.ability.service.impl.EmployeeExperienceService;
import com.ability.util.ValidationHelper;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/v1/api")
@Slf4j
public class EmployeeExperienceRestController {
    
    @Autowired
    private EmployeeExperienceService experienceService;

    @RequestMapping(value = "/employee/{employeeId}/experience", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmployeeExperience(@PathVariable String employeeId,@RequestBody Experience experience) {
        try {
            ValidationHelper.validateEmployeeId(employeeId);
            experience = experienceService.createExployeeExperience(Long.parseLong(employeeId),experience);
            return ResponseEntity.status(HttpStatus.OK).body(experience);
        } catch (ProcessingException e) {
            log.error("Exception::createEmployeeExperience::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
            log.error("RequestBody::"+experience.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
        }
    }
    
    @RequestMapping(value = "/employee/{employeeId}/experience", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployeeExperience(@PathVariable String employeeId) {
        try {
            ValidationHelper.validateEmployeeId(employeeId);
            List<Experience> experiences = experienceService.getEmployeeExperiences(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(experiences);
        } catch (ProcessingException e) {
            log.error("Exception::getEmployeeExperience::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
        }
    }
    
    @RequestMapping(value = "/employee/{employeeId}/experience/{experienceId}", produces = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployeeExperience(@PathVariable String employeeId,@PathVariable String experienceId) {
        try {
            ValidationHelper.validateEmployeeId(employeeId);
            int count = experienceService.deleteEmployeeExperience(Long.parseLong(employeeId),Long.parseLong(experienceId));
           if(count > 0) {
               return ResponseEntity.status(HttpStatus.OK).body(count);
           }
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ProcessingException e) {
            log.error("Exception::deleteEmployeeExperience::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
        }
    }
    
    
    
     @RequestMapping(value = "/employee/{employeeId}/experience", produces = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployeeExperience(@PathVariable String employeeId) {
         try {
             ValidationHelper.validateEmployeeId(employeeId);
             int count = experienceService.deleteEmployeeExperience(Long.parseLong(employeeId),0);
            if(count > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(count);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         } catch (ProcessingException e) {
             log.error("Exception::deleteEmployeeExperience::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
         }
    }
}
