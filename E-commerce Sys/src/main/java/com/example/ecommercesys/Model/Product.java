package com.example.ecommercesys.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    //This class has been reviewed.

    @NotNull
    @NotEmpty(message = "Product ID must not be empty")
    private String id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive number")
    private double price;

    @NotNull(message = "CategoryService ID must not be empty")
    private String categoryID;
}
