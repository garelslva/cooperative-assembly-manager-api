package br.com.assembly.api.exception.handler;

import br.com.assembly.api.exception.BadRequestException;
import br.com.assembly.api.exception.GenericRuntimeException;
import br.com.assembly.api.exception.InternalServerErrorException;
import br.com.assembly.core.exception.MessageError;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

public class ValidationExceptionHandler {

    private ValidationExceptionHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static GenericRuntimeException handledFieldParam(BindException exception){
        var bind = exception;
        List<String> erros = new ArrayList<>();
        bind.getAllErrors().forEach( erro -> {
            if (erro instanceof FieldError) {
                FieldError field = (FieldError) erro;
                erros.add(field.getField());
            }
        });
        var errorType = MessageError.INVALID_FIELD_TYPE;
        if (erros.size() > 1){
            errorType = MessageError.INVALID_FIELDS_TYPE;
        }
        return new BadRequestException(errorType, MessageError.VALIDATION_FIELDS, erros);
    }

    public static GenericRuntimeException handledFieldParam(MethodArgumentTypeMismatchException exception){
        var bind = exception;
        var errorType = MessageError.INVALID_FIELD_TYPE;
        var errorMsg = "Parameters: '".concat(bind.getName()).concat("' ");
        return new BadRequestException(MessageError.VALIDATION_FIELDS, errorType, errorMsg);
    }

    public static GenericRuntimeException handledFieldParam(HttpMessageNotReadableException exception){
        if (exception.getMessage().contains(MessageError.REQUIRED_REQUEST_BODY_IS_MISSING)){
            var errorType = MessageError.INVALID_REQUEST_TYPE;
            var errorMsg = MessageError.REQUIRED_REQUEST_BODY_IS_MISSING;
            return new BadRequestException(MessageError.VALIDATION_FIELDS, errorType, errorMsg);
        }
        return new InternalServerErrorException();
    }
}
