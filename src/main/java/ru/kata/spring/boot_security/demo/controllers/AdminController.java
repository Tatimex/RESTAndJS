package ru.kata.spring.boot_security.demo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
//@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        String email = principal.getName();
        User admin = userService.getUserByEmail(email);
        UserDTO adminDTO = modelMapper.map(admin, UserDTO.class);
        return ResponseEntity.ok(adminDTO);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<UserDTO> allUsersDTO = allUsers.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(allUsersDTO);
    }

    @PostMapping("/save-user")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody UserDTO userDTO,
                                         @RequestParam(value = "rolesNames") String[] roles) {
        User user = modelMapper.map(userDTO, User.class);
        user.setRoles(userDTO.getRoles().stream()
                .map(roleService::getByRoleName)
                .collect(Collectors.toSet()));
        userService.saveUser(user, roles);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PostMapping("/save-user")
//    public ResponseEntity<Void> saveUser(@Valid @RequestBody UserDTO userDTO) {
//        User user = modelMapper.map(userDTO, User.class);
//        user.setRoles(userDTO.getRoles().stream()
//                .map(roleService::getByRoleName)
//                .collect(Collectors.toSet()));
//        userService.saveUser(user, userDTO.getRoles().toArray(new String[0]));
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @GetMapping("/update-info/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        Map<String, Object> response = new HashMap<>();
        response.put("user", userDTO);
        response.put("rolesNames", roleService.getAllRoles());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}






















//    @Controller
//    @RequestMapping("/api/admin")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public class AdminController {
//
//        private final UserService userService;
//        private final RoleService roleService;
//
//        @Autowired
//        public AdminController(UserService userService, RoleService roleService) {
//            this.userService = userService;
//            this.roleService = roleService;
//        }
//        @GetMapping
//        public String user(Principal principal, Model model) {
//            String email = principal.getName();
//            User admin = userService.getUserByEmail(email);
//            model.addAttribute("admin", admin);
//            model.addAttribute("roles", admin.getRoles());
//            return "all-users";
//        }
//
//        @GetMapping("/all-users")
//        public String showAllUsers(Model model) {
//            List<User> allUsers = userService.getAllUsers();
//            model.addAttribute("allUsers", allUsers);
//            return "all-users";
//        }
//        @GetMapping("/addNewUser")
//        public String addNewUser(Model model) {
//            model.addAttribute("user", new User());
//            model.addAttribute("rolesNames", roleService.getAllRoles());
//            return "user-info";
//        }
//
//        @PostMapping("/saveUser")
//        public String saveUser(@ModelAttribute("user") User user, @RequestParam(value = "rolesNames") String[] roles) {
//            userService.saveUser(user, roles);
//            return "redirect:/admin/all-users";
//        }
//        @GetMapping("/updateInfo/{id}")
//        public String updateUser(@PathVariable("id") Long id, Model model) {
//            model.addAttribute("user", userService.getUserById(id));
//            model.addAttribute("rolesNames", roleService.getAllRoles());
//            return "user-info";
//        }
//
//        @GetMapping("/deleteUser/{id}")
//        public String deleteUser(@PathVariable("id") Long id) {
//            userService.deleteUserById(id);
//            return "redirect:/admin/all-users";
//    }
//}
