package com.example.rest.dao;

import com.example.rest.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {

    Role findByRole(String roleName);

}
