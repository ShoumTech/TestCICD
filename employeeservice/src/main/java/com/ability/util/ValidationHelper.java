package com.ability.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.ability.exception.ProcessingException;
import com.ability.model.ErrorMessage;

public class ValidationHelper {

    public static ErrorMessage  constructErrorResponse(ProcessingException e) {
        ErrorMessage em = new ErrorMessage();
        em.setErrorType(e.getErrorType());
        em.setMessageCode(e.getMessage());
        em.setStackTrace(ExceptionUtils.getStackTrace(e));
        return em;
    }
    
    public static void validateEmployeeId(String employeeId) throws ProcessingException {
        if(StringUtils.isEmpty(employeeId) || !StringUtils.isNumeric(employeeId)) {
            throw new ProcessingException("INVALID_EMPLOYEE_ID","Employee id can't be null or string");
        }
    }
}
