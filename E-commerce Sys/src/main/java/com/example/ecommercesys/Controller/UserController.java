package com.example.ecommercesys.Controller;

import com.example.ecommercesys.API.ApiResponse;
import com.example.ecommercesys.Model.Product;
import com.example.ecommercesys.Model.User;
import com.example.ecommercesys.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        return userService.updateUser(id, user) ? ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User updated successfully")) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("User not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id) ? ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User deleted successfully")) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("User not found"));
    }


    //This method has been reviewed.

    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {

        return userService.buyProduct(userId, productId, merchantId) ? ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Purchase successful")) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Purchase failed: invalid IDs, insufficient stock, or low balance"));
    }


                          // =============================================================
//                                                  Second_End_Point
                          // =============================================================
    @GetMapping("/purchase-history")
    public ResponseEntity<?> getPurchaseHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPurchaseHistory());
    }
    //*****************************************************************************


    // =============================================================
//                                                  Fourth_End_Point
    // =============================================================
    @GetMapping("/sales-summary")
    public ResponseEntity<?> getProductSalesSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getProductSalesSummary());
    }
    //*****************************************************************************


            // =============================================================
//                                   Fifth_End_Point
//
            // =============================================================

    @GetMapping("/products-within-budget/{userId}")
    public ResponseEntity<?> getProductsWithinBudget(@PathVariable String userId) {
        ArrayList<Product> products = userService.getProductsWithinUserBudget(userId);

        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found or no products within budget"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


}
