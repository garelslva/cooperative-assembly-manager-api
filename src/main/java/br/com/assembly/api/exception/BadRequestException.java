package br.com.assembly.api.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestException extends GenericRuntimeException {

    private static final String ERROR_MESSAGE = "Invalid request";

    private static final String ERROR_TYPE = "BAD_REQUEST";

    private static final String ERROR_CODE = "INVALID_DATA";

    public BadRequestException() {
        super(ERROR_TYPE, ERROR_CODE, ERROR_MESSAGE, HttpStatus.BAD_REQUEST.value());
    }

    public BadRequestException(String errorMessage) {
        super(ERROR_TYPE, ERROR_CODE, errorMessage, HttpStatus.BAD_REQUEST.value());
    }

    public BadRequestException(String errorType, String errorCode, List<String> errorMessage){
        super(errorType, errorCode, errorMessage, HttpStatus.BAD_REQUEST.value());
    }

    public BadRequestException(String errorType, String errorCode, String errorMessage){
        super(errorType, errorCode, errorMessage, HttpStatus.BAD_REQUEST.value());
    }

    public BadRequestException(String errorType, String errorCode, String errorMessage, int httpStatus) {
        super(errorType, errorCode, errorMessage, httpStatus);
    }

}

