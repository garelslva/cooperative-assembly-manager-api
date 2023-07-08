package br.com.assembly.api.exception.dto;

import java.io.Serializable;

public class ResponsePayloadError implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    private String errorType;

    private String errorCode;

    private Object messages;

    private Integer httpStatus;

    public static ResponsePayloadError of(){
        return new ResponsePayloadError();
    }

    public String getErrorType() {
        return errorType;
    }

    public ResponsePayloadError setErrorType(String errorType) {
        this.errorType = errorType;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ResponsePayloadError setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Object getMessages() {
        return messages;
    }

    public ResponsePayloadError setMessages(Object messages) {
        this.messages = messages;
        return this;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public ResponsePayloadError setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }
}
