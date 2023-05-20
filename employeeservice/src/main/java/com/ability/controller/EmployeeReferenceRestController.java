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
import com.ability.model.Reference;
import com.ability.service.impl.EmployeeReferenceService;
import com.ability.util.ValidationHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api")
@Slf4j
public class EmployeeReferenceRestController {
    
    @Autowired
    private EmployeeReferenceService referenceService;

    @RequestMapping(value = "/employee/{employeeId}/reference", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmployeeReference(@PathVariable String employeeId,@RequestBody Reference reference) {
        try {
            ValidationHelper.validateEmployeeId(employeeId);
            reference = referenceService.createExployeeReference(Long.parseLong(employeeId),reference);
            return ResponseEntity.status(HttpStatus.OK).body(reference);
        } catch (ProcessingException e) {
            log.error("Exception::createEmployeeReference::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
            log.error("RequestBody::"+reference.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
        }
    }
    
    @RequestMapping(value = "/employee/{employeeId}/reference", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployeeReferences(@PathVariable String employeeId) {
        try {
            ValidationHelper.validateEmployeeId(employeeId);
            List<Reference> references = referenceService.getEmployeeReferences(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(references);
        } catch (ProcessingException e) {
            log.error("Exception::getEmployeeReferences::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
        }
    }
    

    
    @RequestMapping(value = "/employee/{employeeId}/reference/{referenceId}", produces = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployeeReference(@PathVariable String employeeId,@PathVariable String referenceId) {
        try {
            ValidationHelper.validateEmployeeId(employeeId);
            int count = referenceService.deleteEmployeeReference(Long.parseLong(employeeId),Long.parseLong(referenceId));
           if(count > 0) {
               return ResponseEntity.status(HttpStatus.OK).body(count);
           }
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ProcessingException e) {
            log.error("Exception::deleteEmployeeReference::EmployeeId::"+employeeId+"::ReferenceId::"+referenceId+"::Error::"+ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
        }
    }
    
    
    
     @RequestMapping(value = "/employee/{employeeId}/reference", produces = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployeeReference(@PathVariable String employeeId) {
         try {
             ValidationHelper.validateEmployeeId(employeeId);
             int count = referenceService.deleteEmployeeReference(Long.parseLong(employeeId),0);
            if(count > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(count);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         } catch (ProcessingException e) {
             log.error("Exception::deleteEmployeeReference::EmployeeId::"+employeeId+"::Error::"+ExceptionUtils.getMessage(e));
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ValidationHelper.constructErrorResponse(e));
         }
    }
  
}
