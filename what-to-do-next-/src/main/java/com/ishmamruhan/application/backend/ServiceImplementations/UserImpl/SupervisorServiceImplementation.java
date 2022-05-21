package com.ishmamruhan.application.backend.ServiceImplementations.UserImpl;

import com.ishmamruhan.application.backend.DAO.UserDao;
import com.ishmamruhan.application.backend.DTO.User;
import com.ishmamruhan.application.backend.Services.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupervisorServiceImplementation extends CommonServiceImplementation implements SupervisorService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllActiveUsers(String id) {
        return getAllUsers(id).stream()
                .filter(user -> user.getIsActive())
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers(String id) {
        List<User> users = userDao.findAll();

        return users.stream()
                .filter(user -> {
                    User user1 = user.getAssciatedWith();

                    if(user1.getId().equals(id))return true;
                    else return false;

                }).collect(Collectors.toList());
    }

}
