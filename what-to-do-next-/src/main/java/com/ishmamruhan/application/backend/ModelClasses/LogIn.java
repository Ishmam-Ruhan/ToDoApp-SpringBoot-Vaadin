package com.ishmamruhan.application.backend.ModelClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class LogIn {
    private String username;
    private String password;
}

