package com.example.ecommercesys.Controller;

import com.example.ecommercesys.API.ApiResponse;
import com.example.ecommercesys.Model.Merchant;
import com.example.ecommercesys.Model.MerchantStock;
import com.example.ecommercesys.Model.Product;
import com.example.ecommercesys.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant/stock")
@RequiredArgsConstructor
public class MerchantStockController {

    //This class has been reviewed.

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> getStocks() {
        return ResponseEntity.status(HttpStatus.OK).body(merchantStockService.getStocks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStock(@Valid @RequestBody MerchantStock stock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        merchantStockService.addStock(stock);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Stock added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStock(@PathVariable String id,
                                         @Valid @RequestBody MerchantStock stock,
                                         Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        return merchantStockService.updateStock(id, stock)
                ? ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Stock updated successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Stock not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable String id) {
        return merchantStockService.deleteStock(id)
                ? ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Stock deleted successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Stock not found"));
    }


    //This method has been reviewed.
    @PutMapping("/add-stock/{productId}/{merchantId}/{amount}")
    public ResponseEntity<?> addStockToMerchant(@PathVariable String productId,
                                                @PathVariable String merchantId,
                                                @PathVariable int amount) {
        if (amount < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Amount must be at least 1"));
        }


        return merchantStockService.addStockToMerchant(productId, merchantId, amount)
                ? ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Stock updated successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid product ID, merchant ID, or stock entry not found"));
    }




                          // =============================================================
//                                                  First_End_Point
                          // =============================================================

    @GetMapping("/available-merchants/{productId}")
    public ResponseEntity<?> getAvailableMerchants(@PathVariable String productId) {
        ArrayList<Merchant> merchants = merchantStockService.getAvailableMerchantsForProduct(productId);

        if (merchants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No available merchants found for the given product ID"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(merchants);
    }

                          // =============================================================
                          // =============================================================





    //Updated End_Point According to instructor notes

                           // =============================================================
//                                                  Third_End_Point
                          // =============================================================
    @GetMapping("/low-stock/{threshold}")
    public ResponseEntity<?> getProductsBelowThreshold(@PathVariable int threshold,@PathVariable String merchantId) {
        ArrayList<Product> products = merchantStockService.getProductsBelowStockThreshold(threshold,merchantId);

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No products found below the given stock threshold"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

                           // =============================================================
                            // =============================================================

}
