package com.example.rest.service;



import com.example.rest.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


public interface RoleService {

    List<Role> getAllRoles();

    void saveRole(Role role);

    void deleteRoleById(Long id);

    Role getRoleById(Long id);

    Role getByRoleName(String roleName);

}
