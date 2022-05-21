package com.ishmamruhan.application.backend.ExceptionManagement.Errors;

import com.ishmamruhan.application.backend.ExceptionManagement.ErrorTemplate;
import com.ishmamruhan.application.backend.ModelClasses.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResponseEntityError extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Object> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error -> {
            ErrorTemplate errorTemplate = new ErrorTemplate();
            errorTemplate.setDetails(error.getDefaultMessage());
            errors.add(errorTemplate);
        }));

        Response response = new Response();

        response.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setObjects(errors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

}
