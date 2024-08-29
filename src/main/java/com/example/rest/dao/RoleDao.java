package com.example.rest.dao;

import com.example.rest.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

    Role findByRole(String roleName);

}
