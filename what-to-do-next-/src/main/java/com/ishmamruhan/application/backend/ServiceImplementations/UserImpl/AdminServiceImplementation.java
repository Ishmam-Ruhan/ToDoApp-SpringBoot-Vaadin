package com.ishmamruhan.application.backend.ServiceImplementations.UserImpl;

import com.ishmamruhan.application.backend.DAO.UserDao;
import com.ishmamruhan.application.backend.DTO.Role;
import com.ishmamruhan.application.backend.DTO.User;
import com.ishmamruhan.application.backend.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class AdminServiceImplementation extends CommonServiceImplementation implements AdminService {


    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllActiveUsers() {
        List<User> users = userDao.findAll();

        return users.stream()
                .filter(user -> user.getIsActive())
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> getAllSupervisors() {
        List<User> users = userDao.findAll();

        return users.stream()
                .filter(user -> {
                    Set<Role> roles = user.getRoles();
                    return roles.stream()
                            .anyMatch(role -> role.getName().equals("SUPERVISOR"));
                }).collect(Collectors.toList());
    }

    @Override
    public List<User> getAllActiveSupervisors() {
        return getAllSupervisors().stream()
                .filter(user -> user.getIsActive())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSupervisor(User user) {
        deleteUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }
}
