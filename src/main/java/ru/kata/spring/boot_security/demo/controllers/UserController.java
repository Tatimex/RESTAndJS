package ru.kata.spring.boot_security.demo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequestMapping("/api/user")
//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUserInfo(Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

//    @PostMapping("/update")
//    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserDTO userDTO) {
//        User user = modelMapper.map(userDTO, User.class);
//        userService.updateUser(user);
//        return ResponseEntity.noContent().build();
//    }
}

//@Controller
//@RequestMapping("/user")
//public class UserController {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping
//    public String user(Principal principal, Model model) {
//        String email = principal.getName();
//        User user = userService.getUserByEmail(email);
//        model.addAttribute("user", user);
//        return "one-user";
//    }
//}
