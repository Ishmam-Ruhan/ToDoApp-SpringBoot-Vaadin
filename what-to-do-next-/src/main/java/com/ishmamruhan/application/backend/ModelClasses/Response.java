package com.ishmamruhan.application.backend.ModelClasses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class Response {
    private int responseCode;
    private List<Object> objects;
    private Object object;
}
