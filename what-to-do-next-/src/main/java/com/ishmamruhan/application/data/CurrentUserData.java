package com.ishmamruhan.application.data;

import com.ishmamruhan.application.backend.DTO.Role;
import com.ishmamruhan.application.backend.DTO.User;
import com.ishmamruhan.application.backend.Security.Configurations.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class CurrentUserData {

    private AuthenticatedUser authenticatedUser;

    @Autowired
    public CurrentUserData(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public CurrentUserData() {
    }

    public String getUserPosition(){
        Optional<User> maybeUser = authenticatedUser.get();
        if(maybeUser.isPresent()){
            User user = maybeUser.get();

            Set<Role> roles = user.getRoles();

            int min = Integer.MAX_VALUE; String position="";

            for(Role role: roles){
                if(role.getName().charAt(0)<min){
                    position = role.getName();
                    min = role.getName().charAt(0);
                }
            }


            if(position.equals("USER")){
                return "USER";
            }
            else if(position.equals("SUPERVISOR")){
                return "SUPERVISOR";
            }
            else if(position.equals("ADMIN")){
                return "ADMIN";
            }
        }
        return "No User Found!";
    }
}
