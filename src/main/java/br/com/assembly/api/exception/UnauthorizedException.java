package br.com.assembly.api.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GenericRuntimeException {

    private static final String ERROR_MESSAGE = "Access no allowed";

    private static final String ERROR_TYPE = "FORBIDDEN";

    private static final String ERROR_CODE = "NOT_ALLOWED";

    public UnauthorizedException() {
        super(ERROR_TYPE, ERROR_CODE, ERROR_MESSAGE, HttpStatus.UNAUTHORIZED.value());
    }

    public UnauthorizedException(String errorMessage) {
        super(ERROR_TYPE, ERROR_CODE, errorMessage, HttpStatus.UNAUTHORIZED.value());
    }

    public UnauthorizedException(String errorType, String errorCode, String errorMessage, int httpStatus) {
        super(errorType, errorCode, errorMessage, httpStatus);
    }

}
