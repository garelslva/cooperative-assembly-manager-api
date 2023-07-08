package br.com.assembly.core.exception;

import br.com.assembly.api.exception.GenericRuntimeException;
import org.springframework.http.HttpStatus;

public class MergeConflictException extends GenericRuntimeException {

    private static final String ERROR_MESSAGE = "Conflict error occurred";

    private static final String ERROR_TYPE = "INTERNAL_SERVER_ERROR";

    private static final String ERROR_CODE = "SERVER_ERROR";

    public MergeConflictException() {
        super(ERROR_TYPE, ERROR_CODE, ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public MergeConflictException(String errorType, String errorCode, String errorMessage, int httpStatus) {
        super(errorType, errorCode, errorMessage, httpStatus);
    }

    public MergeConflictException(Throwable throwable) {
        super(ERROR_TYPE, ERROR_CODE, throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), throwable);
    }
}
