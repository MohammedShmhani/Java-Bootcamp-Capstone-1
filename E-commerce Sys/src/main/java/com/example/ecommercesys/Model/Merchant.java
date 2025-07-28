package com.example.ecommercesys.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {


    //This class has been reviewed.

    @NotEmpty(message = "Merchant ID must not be empty")
    private String id;

    @NotBlank(message = "Merchant name must not be blank")
    @Size(min = 3, message = "Merchant name must be at least 3 characters long")
    private String name;

}
