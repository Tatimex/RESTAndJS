package com.example.rest.service;

import com.example.rest.model.Role;

import java.util.List;


public interface RoleService {

    List<Role> getAllRoles();

    void saveRole(Role role);

    void deleteRoleById(Long id);

    Role getRoleById(Long id);

    Role getByRoleName(String roleName);

}
