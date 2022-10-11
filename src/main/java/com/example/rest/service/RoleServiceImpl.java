package com.example.rest.service;

import com.example.rest.dao.RoleDao;
import com.example.rest.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {
private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public void saveRole(Role role) {
roleDao.save(role);
    }

    @Override
    public void deleteRoleById(Long id) {
roleDao.deleteById(id);
    }

    @Override
    public Role getRoleById(Long id) {
        Role role = null;
        Optional<Role> optional = roleDao.findById(id);
        if(optional.isPresent()){
            role = optional.get();
        }
        return role;
    }

    @Override
    public Role getByRoleName(String roleName) {
        return roleDao.findByRole(roleName);
    }
}
