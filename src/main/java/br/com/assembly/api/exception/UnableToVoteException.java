package br.com.assembly.api.exception;

import org.springframework.http.HttpStatus;

public class UnableToVoteException  extends GenericRuntimeException {

    public static final String ERROR_MESSAGE = "Unable to vote";

    public static final String ERROR_TYPE = "UNABLE_TO_VOTE";

    public static final String ERROR_CODE = "BAD_REQUESTED_BUSINESS";

    public UnableToVoteException() {
        super(ERROR_TYPE, ERROR_CODE, ERROR_MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    public UnableToVoteException(String message) {
        super(ERROR_TYPE, ERROR_CODE, message, HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    public UnableToVoteException(String errorType, String errorCode, String errorMessage, int httpStatus) {
        super(errorType, errorCode, errorMessage, httpStatus);
    }

    public UnableToVoteException(Throwable throwable) {
        super(ERROR_TYPE, ERROR_CODE, throwable.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value(), throwable);
    }
}
