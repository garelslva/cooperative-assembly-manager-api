package br.com.assembly.api.exception.handler;

import br.com.assembly.api.exception.BusinessException;
import br.com.assembly.api.exception.GenericRuntimeException;
import br.com.assembly.api.exception.InternalServerErrorException;
import br.com.assembly.api.exception.NoContentException;
import br.com.assembly.api.exception.BadRequestException;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.api.exception.UnableToVoteException;
import br.com.assembly.api.exception.UnauthorizedException;
import br.com.assembly.api.exception.dto.ResponsePayloadError;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class ExceptionAdviceHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePayloadError> contextBadRequestException(Exception exception,
                                                                           HttpServletRequest request, HttpServletResponse response) {

        log.error(exception.getMessage(), Logger.ROOT_LOGGER_NAME);
        GenericRuntimeException genericBadRequest = null;
        if (exception instanceof BindException){
            genericBadRequest = ValidationExceptionHandler.handledFieldParam((BindException) exception);
        }
        if (exception instanceof MethodArgumentTypeMismatchException){
            genericBadRequest = ValidationExceptionHandler.handledFieldParam((MethodArgumentTypeMismatchException) exception);
        }
        if (exception instanceof HttpMessageNotReadableException){
            genericBadRequest = ValidationExceptionHandler.handledFieldParam((HttpMessageNotReadableException) exception);
        }
        if (genericBadRequest != null) {
            return ResponseEntity.status(genericBadRequest.getEntity().getHttpStatus()).body(genericBadRequest.getEntity());
        }

        var result = new InternalServerErrorException();
        return ResponseEntity.status(result.getEntity().getHttpStatus()).body(result.getEntity());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ResponsePayloadError> contextBadRequestException(InternalServerErrorException exception,
                                                                           HttpServletRequest request, HttpServletResponse response) {
        return responseEntityException(exception);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponsePayloadError> contextBadRequestException(BadRequestException exception,
                                                                           HttpServletRequest request, HttpServletResponse response) {
        return responseEntityException(exception);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponsePayloadError> contextBadRequestException(BusinessException exception,
                                                                           HttpServletRequest request, HttpServletResponse response) {
        return responseEntityException(exception);
    }

    @ExceptionHandler(UnableToVoteException.class)
    public ResponseEntity<ResponsePayloadError> contextBadRequestException(UnableToVoteException exception,
                                                                           HttpServletRequest request, HttpServletResponse response) {
        return responseEntityException(exception);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ResponsePayloadError> contextBadRequestException(NoContentException exception,
                                                                           HttpServletRequest request, HttpServletResponse response) {
        return responseEntityException(exception);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponsePayloadError> contextBadRequestException(NotFoundException exception,
                                                                           HttpServletRequest request, HttpServletResponse response) {
        return responseEntityException(exception);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponsePayloadError> contextBadRequestException(UnauthorizedException exception,
                                                                           HttpServletRequest request, HttpServletResponse response) {
        return responseEntityException(exception);
    }

    private ResponseEntity<ResponsePayloadError> responseEntityException(GenericRuntimeException exception) {
        log.error(exception.getMessage(), Logger.ROOT_LOGGER_NAME);
        return ResponseEntity.status(exception.getEntity().getHttpStatus()).body(exception.getEntity());
    }

}
