package com.ishmamruhan.application.backend.Services;


import com.ishmamruhan.application.backend.DTO.User;
import org.springframework.stereotype.Service;


@Service
public interface AuthenticationService {
    String registration(User user);
}
