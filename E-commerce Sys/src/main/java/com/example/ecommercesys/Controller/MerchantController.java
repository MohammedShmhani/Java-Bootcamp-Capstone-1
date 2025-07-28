package com.example.ecommercesys.Controller;

import com.example.ecommercesys.API.ApiResponse;
import com.example.ecommercesys.Model.Merchant;
import com.example.ecommercesys.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    //This class has been reviewed.


    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants() {
        return ResponseEntity.status(HttpStatus.OK).body(merchantService.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        merchantService.addMerchant(merchant);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Merchant added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        return merchantService.updateMerchant(id, merchant) ?
                ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Merchant updated successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Merchant not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        return merchantService.deleteMerchant(id) ?
                ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Merchant deleted successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Merchant not found"));
    }

}
