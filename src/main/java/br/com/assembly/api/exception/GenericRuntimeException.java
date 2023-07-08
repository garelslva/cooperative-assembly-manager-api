package br.com.assembly.api.exception;

import br.com.assembly.api.exception.dto.ResponsePayloadError;

import java.util.List;

public class GenericRuntimeException extends RuntimeException {

    protected ResponsePayloadError entity;

    public GenericRuntimeException(final String errorCode, final String errorType, final String errorMessage, final int httpStatus) {
        super(errorMessage);
        this.entity = ResponsePayloadError.of()
                .setErrorCode(errorType)
                .setErrorType(errorCode)
                .setHttpStatus(httpStatus)
                .setMessages(errorMessage);
    }

    public GenericRuntimeException(final String errorType, final String errorCode, final List<String> messages, final int httpStatus) {
        super();
        this.entity = ResponsePayloadError.of()
                .setErrorCode(errorCode)
                .setErrorType(errorType)
                .setHttpStatus(httpStatus)
                .setMessages(messages);
    }

    public GenericRuntimeException(final String errorType, final String errorCode, final String errorMessage, final int httpStatus,
                                   final Throwable throwable) {
        super(errorMessage, throwable);
        this.entity = ResponsePayloadError.of()
                .setErrorCode(errorCode)
                .setErrorType(errorType)
                .setHttpStatus(httpStatus)
                .setMessages(errorMessage);
    }

    public GenericRuntimeException(final String errorType, final String errorCode, final List<String> messages, final int httpStatus,
                                   final Throwable throwable) {
        super(throwable);
        this.entity = ResponsePayloadError.of()
                .setErrorCode(errorCode)
                .setErrorType(errorType)
                .setHttpStatus(httpStatus)
                .setMessages(messages);
    }

    public ResponsePayloadError getEntity() {
        return entity;
    }

    public void setEntity(ResponsePayloadError entity) {
        this.entity = entity;
    }
}
