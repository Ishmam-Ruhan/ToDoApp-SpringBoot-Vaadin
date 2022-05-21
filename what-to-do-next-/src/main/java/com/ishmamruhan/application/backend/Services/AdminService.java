package com.ishmamruhan.application.backend.Services;

import com.ishmamruhan.application.backend.DTO.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService{

    List<User> getAllActiveUsers();

    List<User> getAllUsers();

    List<User> getAllSupervisors();

    List<User> getAllActiveSupervisors();

    void deleteSupervisor(User user);

    void deleteUser(User user);
}
