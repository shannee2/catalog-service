package com.catalog.controller;

import com.catalog.dto.user.UserRequest;
import com.catalog.dto.user.UserResponse;
import com.catalog.exceptions.users.UserNotFoundException;
import com.catalog.model.user.User;
import com.catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        UserResponse response = userService.registerUser(userRequest);
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest) throws UserNotFoundException, AccessDeniedException {
        UserResponse response = userService.verify(userRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) throws UserNotFoundException {
        return userService.getUserByUsername(username);
    }
}