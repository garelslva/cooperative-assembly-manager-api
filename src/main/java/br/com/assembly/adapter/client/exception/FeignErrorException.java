package br.com.assembly.adapter.client.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeignErrorException extends RuntimeException {

    private final int httpStatus;
    private final String url;

    public FeignErrorException(String message, int httpStatus, String url) {
        super(message);
        this.httpStatus = httpStatus;
        this.url = url;
    }
}
