package com.ishmamruhan.application.backend.Security.Service;

import com.ishmamruhan.application.backend.DAO.UserDao;
import com.ishmamruhan.application.backend.DTO.User;
import com.ishmamruhan.application.backend.ExceptionManagement.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, CustomError {

        User user = null;

        user = userDao.findByUsername(username);

        if(user == null){
            user = userDao.findByEmail(username);
        }

        if(user == null){
            throw new BadCredentialsException("User not Found!");
        }

        return new CustomUserDetail(user);
    }
}
