package com.mcode.transferservice.exceptions;

import com.mcode.transferservice.models.responses.GenericResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GenericControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        final GenericResponses<Object> error = GenericResponses.builder().
                isRequestSuccessful(false).
                message(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {BadRequestException.class, InterruptedException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        final GenericResponses<Object> error = GenericResponses.builder().
                isRequestSuccessful(false).
                message(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();

        Optional<FieldError> error = bindingResult.getFieldErrors().stream().findFirst();

        String firstErrorMessage = "Bad Credentials";
        if (error.isPresent()) {
            firstErrorMessage = error.get().getDefaultMessage() + " : " + error.get().getField();
        }

        final GenericResponses<Object> errorResponse = GenericResponses.builder().
                isRequestSuccessful(false).
                message(firstErrorMessage)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
