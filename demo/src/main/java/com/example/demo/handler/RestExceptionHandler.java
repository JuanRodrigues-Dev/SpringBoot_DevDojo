package com.example.demo.handler;


import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.BadRequestExceptionDetails;
import com.example.demo.exception.ExceptionDetails;
import com.example.demo.exception.ValidationExceptionDetails;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException exception){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .dateTime(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Checkn the Documentation")
                        .details(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ValidationExceptionDetails validationExceptionDetails = ValidationExceptionDetails.builder()
                .dateTime(LocalDateTime.now())
                .status(status.value())
                .title("Bad Request Exception, Invalid Fields")
                .details("Check the field errors")
                .developerMessage(exception.getClass().getName())
                .fields(String.valueOf(exception.getBindingResult().getFieldErrors()
                        .stream()
                        .map(FieldError::getField)
                        .toList()))
                .fieldsMessage(String.valueOf(exception.getBindingResult().getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList()))
                .build();

        return handleExceptionInternal(exception, validationExceptionDetails, headers, status, request);
    }
}
