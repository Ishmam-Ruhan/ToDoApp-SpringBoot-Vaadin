package com.ishmamruhan.application.backend.Services;

import com.ishmamruhan.application.backend.DTO.User;

import java.util.List;

public interface SupervisorService{

    List<User> getAllActiveUsers(String id);

    List<User> getAllUsers(String id);

}
