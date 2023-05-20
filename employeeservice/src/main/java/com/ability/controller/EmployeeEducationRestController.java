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
import com.ability.model.Education;
import com.ability.service.impl.EmployeeEducationService;
import com.ability.util.ValidationHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api")
@Slf4j
public class EmployeeEducationRestController {
    
    @Autowired
    private EmployeeEducationService educationService;

    @RequestMapping(value = "/employee/{employeeId}/education", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmployeeEducation(@PathVariable String employeeId,@RequestBody Education education) {
        try {
            ValidationHelper.validateEmployeeId(employeeId);
            education = educationService.createEmployeeEducation(Long.parseLong(employeeId),education);
            return ResponseEntity.status(HttpStatus.OK).body(education);
        } catch (ProcessingException e) {
            log.error("Exception::createEmployeeEducation::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
            log.error("RequestBody::"+education.toString());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
        }
    }
    
    
    @RequestMapping(value = "/employee/{employeeId}/education", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployeeEducation(@PathVariable String employeeId) {
        try {
            ValidationHelper.validateEmployeeId(employeeId);
            List<Education> educationList = educationService.getEmployeeEducationList(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(educationList);
        } catch (ProcessingException e) {
            log.error("Exception::getEmployeeEducation::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
        }
    }
    
    @RequestMapping(value = "/employee/{employeeId}/education/{educationId}", produces = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployeeEducation(@PathVariable String employeeId,@PathVariable String educationId) {
        try {
            ValidationHelper.validateEmployeeId(employeeId);
            int count = educationService.deleteEmployeeEducation(Long.parseLong(employeeId),Long.parseLong(educationId));
           if(count > 0) {
               return ResponseEntity.status(HttpStatus.OK).body(count);
           }
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ProcessingException e) {
            log.error("Exception::deleteEmployeeEducation::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
        }
    }
    
    
    
     @RequestMapping(value = "/employee/{employeeId}/education", produces = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployeeEducation(@PathVariable String employeeId) {
         try {
             ValidationHelper.validateEmployeeId(employeeId);
             int count = educationService.deleteEmployeeEducation(Long.parseLong(employeeId),0);
            if(count > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(count);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         } catch (ProcessingException e) {
             log.error("Exception::deleteEmployeeEducation::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
         }
    }
}
