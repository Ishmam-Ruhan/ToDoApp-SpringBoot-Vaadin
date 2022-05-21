package com.ishmamruhan.application.backend.DAO;


import com.ishmamruhan.application.backend.DTO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User,String>{

    @Query("select u from User u where u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("select u from User u where u.userName = :username")
    User findByUsername(@Param("username") String username);

}
