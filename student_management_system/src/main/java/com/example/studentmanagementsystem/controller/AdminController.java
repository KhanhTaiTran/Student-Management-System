package com.example.studentmanagementsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagementsystem.dto.request.CreateUserRequestDTO;
import com.example.studentmanagementsystem.dto.response.UserResponseDTO;
import com.example.studentmanagementsystem.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/admin/users") // base URL to admin manage users
public class AdminController {
    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // create user
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequestDTO request) {
        UserResponseDTO newUser = userService.createUser(request);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // take the profile of user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // TODO: admin can create course and classroom

}
