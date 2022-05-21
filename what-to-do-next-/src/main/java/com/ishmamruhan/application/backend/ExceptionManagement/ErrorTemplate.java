package com.ishmamruhan.application.backend.ExceptionManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorTemplate {
    private long errorCode;
    private String errorType;
    private String Details;
}
