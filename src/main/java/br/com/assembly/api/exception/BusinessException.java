package br.com.assembly.api.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends GenericRuntimeException {

    public static final String ERROR_MESSAGE = "No accept to rule";

    public static final String ERROR_TYPE = "INVALID_REQUEST";

    public static final String ERROR_CODE = "BAD_REQUESTED_BUSINESS";

    public BusinessException() {
        super(ERROR_TYPE, ERROR_CODE, ERROR_MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    public BusinessException(String message) {
        super(ERROR_TYPE, ERROR_CODE, message, HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    public BusinessException(String errorType, String errorCode, String errorMessage, int httpStatus) {
        super(errorType, errorCode, errorMessage, httpStatus);
    }

    public BusinessException(Throwable throwable) {
        super(ERROR_TYPE, ERROR_CODE, throwable.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value(), throwable);
    }
}
