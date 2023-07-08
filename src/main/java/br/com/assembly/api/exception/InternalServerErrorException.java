package br.com.assembly.api.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends GenericRuntimeException {

    private static final String ERROR_MESSAGE = "Unexpected error occurred";

    private static final String ERROR_TYPE = "INTERNAL_SERVER_ERROR";

    private static final String ERROR_CODE = "SERVER_ERROR";

    public InternalServerErrorException() {
        super(ERROR_TYPE, ERROR_CODE, ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public InternalServerErrorException(final String message) {
        super(ERROR_TYPE, ERROR_CODE, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public InternalServerErrorException(String errorType, String errorCode, String errorMessage, int httpStatus) {
        super(errorType, errorCode, errorMessage, httpStatus);
    }

    public InternalServerErrorException(Throwable throwable) {
        super(ERROR_TYPE, ERROR_CODE, throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), throwable);
    }

}

