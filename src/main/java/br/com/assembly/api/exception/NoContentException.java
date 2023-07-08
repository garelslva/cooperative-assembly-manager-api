package br.com.assembly.api.exception;

import org.springframework.http.HttpStatus;

public class NoContentException extends GenericRuntimeException {

    private static final String ERROR_MESSAGE = "No content payload";

    private static final String ERROR_TYPE = "NO_CONTENT";

    private static final String ERROR_CODE = "NO_BODY";


    public NoContentException() {
        super(ERROR_TYPE, ERROR_CODE, ERROR_MESSAGE, HttpStatus.NO_CONTENT.value());
    }

    public NoContentException(String errorMessage) {
        super(ERROR_TYPE, ERROR_CODE, errorMessage, HttpStatus.NO_CONTENT.value());
    }
}
