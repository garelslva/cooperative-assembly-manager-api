package br.com.assembly.adapter.client.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

public class CpfVerificationConfig {

    @Bean
    public RequestInterceptor requestInterceptorBenefit() {

        return requestTemplate -> {
            requestTemplate
                    .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                    .header("Content-type", MediaType.APPLICATION_JSON_VALUE);
        };
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder decode() {
        return new CustomFeignErrorDecoder();
    }
}
