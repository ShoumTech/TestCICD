package com.ability.exception;

public class ProcessingException extends Throwable {

    /**
     * 
     */
    private String errorType;
    private static final long serialVersionUID = 1L;

    public ProcessingException() {
        super();
    }

    public ProcessingException(String type, String message) {
        super(message);
        this.errorType = type;
    }

    public ProcessingException(Throwable cause) {
        super(cause);
        this.errorType = "INTERNAL_ERROR";
    }

    public String getErrorType() {
        return errorType;
    }


}
