package com.example.studentmanagementsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// admin use to create account
public class CreateUserRequestDTO {

    private String fullName;
    private String username;
    private String email;
    private String studentId;
    private String password;
    private String role;
}
