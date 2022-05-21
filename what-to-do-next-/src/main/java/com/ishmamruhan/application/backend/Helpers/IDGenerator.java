package com.ishmamruhan.application.backend.Helpers;

import java.util.UUID;

public class IDGenerator {
    public static String generateID(){
        return UUID.randomUUID().toString();
    }
}
