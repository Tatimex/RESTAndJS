package com.example.rest.controller;

import com.example.rest.dto.UserDto;
import com.example.rest.model.Role;
import com.example.rest.model.User;
import com.example.rest.service.RoleService;
import com.example.rest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class AdminRest {

    private final UserService userService;
    private final RoleService roleService;

    public AdminRest(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user")
    public User getUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/users/new")
    public User newUser() {
        User blankUser = new User();
        blankUser.setRoles(new HashSet<>(roleService.getAllRoles()));
        return blankUser;
    }

    @PostMapping("/admin/users")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        Set<Role> roleSet = new HashSet<>();
        for (String roleName : userDto.getRolesNames()) {
            roleSet.add(roleService.getByRoleName(roleName));
        }
        User user = new User(userDto);
        user.setRoles(roleSet);
        userService.saveUser(user);
        return new ResponseEntity<>(userService.findByEmail(user.getEmail()), HttpStatus.CREATED);
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long id,
                                           @RequestBody UserDto userDto) {
        Set<Role> rolesSet = new HashSet<>();
        for (String roleName : userDto.getRolesNames()) {
            rolesSet.add(roleService.getByRoleName(roleName));
        }
        User user = new User(userDto);
        user.setRoles(rolesSet);
        user.setId(id);
        userService.updateUser(user);
        User updatedUser = userService.getUserById(id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<List<User>> delete(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}