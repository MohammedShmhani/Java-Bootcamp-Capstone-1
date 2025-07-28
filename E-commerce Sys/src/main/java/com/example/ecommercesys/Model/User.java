package com.example.ecommercesys.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class User {

    //This class has been reviewed.

    @NotEmpty(message = "User ID must not be Empty")
    private String id;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 5, message = "Username must be at least 5 characters long")
    private String username;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain letters and numbers")
    private String password;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Role must not be blank")
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be either Admin or Customer")
    private String role;

    @NotNull(message = "Balance must not be null")
    @Positive(message = "Balance must be a positive number")
    private Double balance;
}
