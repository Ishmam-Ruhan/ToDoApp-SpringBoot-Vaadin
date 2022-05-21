package com.ishmamruhan.application.backend.ExceptionManagement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomError extends RuntimeException{
    private long error_code;
    private String error_type;
    private String error_description;
}
