package br.com.assembly.adapter.client.config;

import br.com.assembly.adapter.client.exception.FeignErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class CustomFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        String responseBody;

        try {
            responseBody = getResponseBodyAsString(response.body());
        } catch (Exception ex) {
            return new FeignErrorException("An error has occurred while trying to parse the body message response.",
                    response.status(), response.request().url());
        }

        if (!StringUtils.hasText(responseBody)) {
            return new FeignErrorException("Message body of exception is empty.",
                    response.status(), response.request().url());
        }

        return createException(responseBody, response.status(), response.request().url());
    }

    private Exception createException(String responseBody, int statusCode, String url){
        HttpStatus httpStatus = HttpStatus.resolve(statusCode);

        if (httpStatus == null) {
            return new FeignErrorException("API returned an unknown status code", statusCode, url);
        }

        if (httpStatus.equals(HttpStatus.UNPROCESSABLE_ENTITY) || httpStatus.equals(HttpStatus.BAD_REQUEST)){
            return new FeignErrorException(responseBody, statusCode, url);
        }

        return new FeignErrorException(responseBody, statusCode, url);
    }

    private String getResponseBodyAsString(final Response.Body body) throws IOException {
        if (body == null) {
            return "";
        }
        return IOUtils.toString(body.asInputStream(),StandardCharsets.UTF_8);
    }

}
