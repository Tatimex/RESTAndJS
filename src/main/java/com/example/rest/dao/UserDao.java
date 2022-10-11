package com.example.rest.dao;


import com.example.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
