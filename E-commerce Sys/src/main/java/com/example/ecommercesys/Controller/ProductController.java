package com.example.ecommercesys.Controller;

import com.example.ecommercesys.API.ApiResponse;
import com.example.ecommercesys.Model.Product;
import com.example.ecommercesys.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    //This method has been reviewed.

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product p, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        productService.addProduct(p);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @Valid @RequestBody Product p, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));


        return productService.updateProduct(id, p) ?
                ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product updated successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Product not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id) ?
                ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product deleted successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Product not found"));
    }
}
