package com.ishmamruhan.application.backend.Configurations;


import com.ishmamruhan.application.backend.DAO.RoleDao;
import com.ishmamruhan.application.backend.DAO.UserDao;
import com.ishmamruhan.application.backend.DTO.Role;
import com.ishmamruhan.application.backend.DTO.User;
import com.ishmamruhan.application.backend.Enums.ROLE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.UUID;

@Configuration
@Component
public class RolesAndAdminInitialization {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    Role admin = new Role();
    Role supervisor = new Role();
    Role _user = new Role();

    @PostConstruct
    public void initializeAdmin(){
        initializeRoles();

        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setEmail("admin@admin.com");
        user.setUserName("admin");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        user.setIsActive(true);
        user.setRoles(Set.of(admin,supervisor,_user));

        userDao.save(user);

    }

    public void initializeRoles(){
        admin.setId(UUID.randomUUID().toString());
        admin.setName(ROLE.ADMIN.toString());

        roleDao.save(admin);

        supervisor.setId(UUID.randomUUID().toString());
        supervisor.setName(ROLE.SUPERVISOR.toString());

        roleDao.save(supervisor);

        _user.setId(UUID.randomUUID().toString());
        _user.setName(ROLE.USER.toString());

        roleDao.save(_user);

    }

    public Role getAdmin() {
        return admin;
    }

    public Role getSupervisor() {
        return supervisor;
    }

    public Role get_user() {
        return _user;
    }
}
