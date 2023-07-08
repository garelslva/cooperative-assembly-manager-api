package br.com.assembly.api.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GenericRuntimeException {

    public static final String ERROR_MESSAGE = "Not found response";

    public static final String ERROR_TYPE = "NOT_FOUND";

    public static final String ERROR_CODE = "RESOURCE_NOT_FOUND";

    public NotFoundException() {
        super(ERROR_TYPE, ERROR_CODE, ERROR_MESSAGE, HttpStatus.NOT_FOUND.value());
    }

    public NotFoundException(String errorMessage) {
        super(ERROR_TYPE, ERROR_CODE, errorMessage, HttpStatus.NOT_FOUND.value());
    }
    
}
