package com.ishmamruhan.application.backend.ServiceImplementations.AuthImpl;

import com.ishmamruhan.application.backend.Configurations.RolesAndAdminInitialization;
import com.ishmamruhan.application.backend.DAO.RoleDao;
import com.ishmamruhan.application.backend.DAO.UserDao;
import com.ishmamruhan.application.backend.DTO.Role;
import com.ishmamruhan.application.backend.DTO.User;
import com.ishmamruhan.application.backend.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Primary
public class AuthServiceImplementations implements AuthenticationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Autowired
    private RoleDao roleDao;

    @Override
    public String registration(User user) {

        user.setId(UUID.randomUUID().toString());
        user.setIsActive(true);
        user.setPassword(encoder.encode(user.getPassword()));

        Set<Role> role = new HashSet<>();
        role.add(roleDao.findByName("USER"));

        user.setRoles(role);

        userDao.save(user);

        return null;
    }
}
