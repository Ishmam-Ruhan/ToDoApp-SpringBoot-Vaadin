package com.ishmamruhan.application.backend.ExceptionManagement.Errors;


import com.ishmamruhan.application.backend.ExceptionManagement.CustomError;
import com.ishmamruhan.application.backend.ExceptionManagement.ErrorTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(CustomError.class)
    public ResponseEntity<Object> myCustomErrorHandler(CustomError customError){

        ErrorTemplate errorTemplate = new ErrorTemplate();

        errorTemplate.setErrorCode(customError.getError_code());
        errorTemplate.setErrorType(customError.getError_type());
        errorTemplate.setDetails(customError.getError_description());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(errorTemplate);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> userNotFoundException(BadCredentialsException user){

        ErrorTemplate errorTemplate = new ErrorTemplate();

        errorTemplate.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorTemplate.setErrorType("NOT-FOUND!");
        errorTemplate.setDetails(user.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(errorTemplate);
    }
}
