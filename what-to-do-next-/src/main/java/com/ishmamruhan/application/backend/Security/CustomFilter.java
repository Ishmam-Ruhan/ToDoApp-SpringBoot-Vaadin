package com.ishmamruhan.application.backend.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishmamruhan.application.backend.DAO.UserDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CustomFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final UserDao userRepo;

    public CustomFilter(AuthenticationManager authManager, UserDao userRepo) {
        super();
        this.authManager = authManager;
        this.userRepo = userRepo;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        String username, password;

        try {
            Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            username = requestMap.get("username");
            password = requestMap.get("password");

            Authentication auth = new UsernamePasswordAuthenticationToken(username,password);

            return authManager.authenticate(auth);
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {


        System.out.println("-------------------------------------------");
        System.out.println("Successfully Logged In!");
        System.out.println("-------------------------------------------");

        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

}
