package com.ability.controller;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ability.exception.ProcessingException;
import com.ability.model.Availability;
import com.ability.model.EmergencyContact;
import com.ability.model.Employee;
import com.ability.model.ErrorMessage;
import com.ability.model.GenerateOneTimePassCodeRequest;
import com.ability.model.GenerateOneTimePassCodeResponse;
import com.ability.model.OneTimePassAuthRequest;
import com.ability.model.OneTimePassAuthResponse;
import com.ability.model.PersonalInfo;
import com.ability.model.SearchRequest;
import com.ability.model.SearchResponse;
import com.ability.model.TaskReviewInfo;
import com.ability.model.TransportInfo;
import com.ability.service.impl.EmployeeProfileService;
import com.ability.service.impl.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api")
@Slf4j
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeProfileService profileService;

    @RequestMapping(value = "/employee/{emailAddress}/minAccount", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> createMinEmployee(@PathVariable String emailAddress) {
        try {
            Employee employee = employeeService.createMinEmployee(emailAddress);
            return ResponseEntity.status(HttpStatus.OK).body(employee);

        } catch (ProcessingException e) {
            log.error("Exception::createMinEmployee::EmailAddress::" + emailAddress + "::Error::"
                    + ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    /**
     * Method to create complete employee details
     * 
     * @param employee
     * @return
     */
    @RequestMapping(value = "/employee/{employeeId}", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployee(@PathVariable String employeeId,
            @RequestParam("fields") String[] fields) {
        try {
            validateEmployeeId(employeeId);
            Employee employee = employeeService.getEmployeeDetails(employeeId, Arrays.asList(fields));
            return ResponseEntity.status(HttpStatus.OK).body(employee);

        } catch (ProcessingException e) {
            log.error("Exception::getEmployee::EmployeeId::" + employeeId + "::Fields::" + fields + "::Error::"
                    + ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    /**
     * Method to create basic employee profile info
     * 
     * @param emailAddress
     * @param personalInfo
     * @return
     */
    @RequestMapping(value = "/employee/{employeeId}/personalInfo", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> createPersonalInfo(@PathVariable String employeeId,
            @RequestBody PersonalInfo personalInfo) {
        try {
            validateEmployeeId(employeeId);
            personalInfo = profileService.createPersonalInfo(Long.parseLong(employeeId), personalInfo);
            return ResponseEntity.status(HttpStatus.OK).body(personalInfo);
        } catch (ProcessingException e) {
            log.error("Exception::createPersonalInfo::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + personalInfo.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    /**
     * Method to create basic employee profile info
     * 
     * @param emailAddress
     * @param personalInfo
     * @return
     */
    @RequestMapping(value = "/employee/{employeeId}/personalInfo", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonalInfo(@PathVariable String employeeId) {
        try {
            validateEmployeeId(employeeId);
            PersonalInfo personalInfo = profileService.getPersonalInfo(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(personalInfo);
        } catch (ProcessingException e) {
            log.error("Exception::getPersonalInfo::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/contact", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmergencyContact(@PathVariable String employeeId,
            @RequestBody EmergencyContact emergencyContact) {
        try {
            validateEmployeeId(employeeId);
            emergencyContact = employeeService.createEmergencyContact(Long.parseLong(employeeId), emergencyContact);
            return ResponseEntity.status(HttpStatus.OK).body(emergencyContact);
        } catch (ProcessingException e) {
            log.error("Exception::createEmergencyContact::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + emergencyContact.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/contact", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmergencyContact(@PathVariable String employeeId) {
        try {
            validateEmployeeId(employeeId);
            EmergencyContact emergencyContact = employeeService.getEmergencyContact(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(emergencyContact);
        } catch (ProcessingException e) {
            log.error("Exception::getEmergencyContact::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/transport", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmployeeTransport(@PathVariable String employeeId,
            @RequestBody TransportInfo transportInfo) {
        try {
            validateEmployeeId(employeeId);
            transportInfo = employeeService.createTransportInfo(Long.parseLong(employeeId), transportInfo);
            return ResponseEntity.status(HttpStatus.OK).body(transportInfo);
        } catch (ProcessingException e) {
            log.error("Exception::createEmployeeTransport::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + transportInfo.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/transport", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployeeTransport(@PathVariable String employeeId) {
        try {
            validateEmployeeId(employeeId);
            TransportInfo transportInfo = employeeService.getEmployeeTransportInfo(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(transportInfo);
        } catch (ProcessingException e) {
            log.error("Exception::getEmployeeTransport::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/availability", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployeeAvailability(@PathVariable String employeeId) {
        try {
            validateEmployeeId(employeeId);
            Availability availability = employeeService.getEmployeeAvailability(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(availability);
        } catch (ProcessingException e) {
            log.error("Exception::getEmployeeAvailability::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/availability", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmployeeAvailability(@PathVariable String employeeId,
            @RequestBody Availability availability) {
        try {
            validateEmployeeId(employeeId);
            availability = employeeService.createEmployeeAvailability(Long.parseLong(employeeId), availability);
            return ResponseEntity.status(HttpStatus.OK).body(availability);
        } catch (ProcessingException e) {
            log.error("Exception::createEmployeeAvailability::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + availability.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/skillset", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmployeeSkillSet(@PathVariable String employeeId,
            @RequestBody TaskReviewInfo taskReviewInfo) {
        try {
            validateEmployeeId(employeeId);
            taskReviewInfo = employeeService.createEmployeeSkillSet(Long.parseLong(employeeId), taskReviewInfo);
            return ResponseEntity.status(HttpStatus.OK).body(taskReviewInfo);
        } catch (ProcessingException e) {
            log.error("Exception::createEmployeeSkillSet::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + taskReviewInfo.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/skillset", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployeeSkillSet(@PathVariable String employeeId) {
        try {
            validateEmployeeId(employeeId);
            TaskReviewInfo taskReviewInfo = employeeService.getEmployeeSkillSet(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(taskReviewInfo);
        } catch (ProcessingException e) {
            log.error("Exception::getEmployeeSkillSet::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/mark-visited", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> markEmployeeVisited(@PathVariable String employeeId, @RequestBody Employee employee) {
        try {
            validateEmployeeId(employeeId);
            boolean status = employeeService.markVisited(employeeId, employee);
            if (status) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (ProcessingException e) {
            log.error("Exception::markEmployeeVisited::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + employee.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/consent", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> updateEmployeeConsent(@PathVariable String employeeId,
            @RequestBody Employee employee) {
        try {
            validateEmployeeId(employeeId);
            boolean status = employeeService.updateConsent(employeeId, employee);
            if (status) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (ProcessingException e) {
            log.error("Exception::updateEmployeeConsent::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + employee.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/declaration", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> updateEmployeeDeclaration(@PathVariable String employeeId,
            @RequestBody Employee employee) {
        try {
            validateEmployeeId(employeeId);
            boolean status = employeeService.updateDeclaration(employeeId, employee);
            if (status) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (ProcessingException e) {
            log.error("Exception::updateEmployeeConsent::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + employee.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/employee/{employeeId}/declaration", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployeeDeclaration(@PathVariable String employeeId) {
        try {
            validateEmployeeId(employeeId);
            Employee emp = employeeService.getDeclaration(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(emp);

        } catch (ProcessingException e) {
            log.error("Exception::getEmployeeDeclaration::EmployeeId::" + employeeId + "::Error::"
                    + ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/search-employees", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> searchEmployee(@RequestBody SearchRequest searchRequest) {
        log.info("Search request start");
        try {
            SearchResponse searchResponse = employeeService.getEmployeeList(searchRequest);
            log.info("Search request end");
            return ResponseEntity.status(HttpStatus.OK).body(searchResponse);
        } catch (ProcessingException e) {
            log.error("Exception::searchEmployee::searchRequest::" + searchRequest + "::Error::"
                    + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + searchRequest.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }

    }

    @RequestMapping(value = "/generate-OTP", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> generateOTP(@RequestBody GenerateOneTimePassCodeRequest request) {
        try {
            GenerateOneTimePassCodeResponse response = employeeService.generateOTP(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ProcessingException e) {
            log.error("Exception::generateOTP::validateOtp::" + request + "::Error::" + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + request.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    @RequestMapping(value = "/authenticate-OTP", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> authenticateOTP(@RequestBody OneTimePassAuthRequest request) {
        try {
            OneTimePassAuthResponse response = employeeService.authenticateOTP(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ProcessingException e) {
            log.error(
                    "Exception::authenticateOTP::validateOtp::" + request + "::Error::" + ExceptionUtils.getMessage(e));
            log.error("RequestBody::" + request.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructErrorResponse(e));
        }
    }

    private ErrorMessage constructErrorResponse(ProcessingException e) {
        ErrorMessage em = new ErrorMessage();
        if (e.getErrorType() != null) {
            em.setErrorType(e.getErrorType());
        } else {
            em.setErrorType("INTERNAL_SERVER_ERROR");
        }
        if (e.getMessage() != null) {
            em.setMessageCode(e.getMessage());
        } else {
            em.setMessageCode(ExceptionUtils.getMessage(e));
        }
        em.setStackTrace(ExceptionUtils.getStackTrace(e));
        return em;
    }

    private void validateEmployeeId(String employeeId) throws ProcessingException {
        if (StringUtils.isEmpty(employeeId) || !StringUtils.isNumeric(employeeId)) {
            throw new ProcessingException("INVALID_EMPLOYEE_ID", "Employee id can't be null or string");
        }
    }
}
