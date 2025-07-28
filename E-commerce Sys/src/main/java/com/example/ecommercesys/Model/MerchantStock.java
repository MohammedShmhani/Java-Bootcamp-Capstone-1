package com.example.ecommercesys.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    //This class has been reviewed.

    @NotEmpty(message = "Stock ID must not be empty")
    private String  id;

    @NotNull(message = "Merchant ID must not be null")
    private String merchantId;

    @NotNull(message = "Product ID must not be null")
    private String productId;



    @NotNull(message = "Stock quantity must not be null")
    @Min(value = 10, message = "Stock must be at least 10 at start")
    private int stock;
}
