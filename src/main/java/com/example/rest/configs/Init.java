package com.example.rest.configs;

import com.example.rest.model.Role;
import com.example.rest.model.User;
import com.example.rest.service.RoleService;
import com.example.rest.service.UserService;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.Collections;


@Component
public class Init {

    private final UserService userService;
    private final RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void postConstruct() {
        User admin = new User();
        admin.setEmail("admin@admin.com");
        admin.setName("Pupa");
        admin.setSurname("Pupuna");
        admin.setAge("34");
        admin.setPassword("user");


        User user = new User();
        user.setEmail("user@user.com");
        user.setName("Fufu");
        user.setSurname("Fufin");
        user.setAge("18");
        user.setPassword("user");


        Role role = new Role(1L, "ROLE_ADMIN");
        Role role2 = new Role(2L, "ROLE_USER");

        roleService.saveRole(role);
        roleService.saveRole(role2);

        admin.setRoles(Collections.singleton(role));
        user.setRoles(Collections.singleton(role2));

        userService.saveUser(admin);
        userService.saveUser(user);
    }

}
