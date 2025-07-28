package com.example.ecommercesys.Controller;

import com.example.ecommercesys.API.ApiResponse;
import com.example.ecommercesys.Model.Category;
import com.example.ecommercesys.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    //This class has been reviewed.
    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category c, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        categoryService.addCategory(c);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @Valid @RequestBody Category c, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));


        return categoryService.updateCategory(id, c) ?
                ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category updated successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Category not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        return categoryService.deleteCategory(id) ?
                ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category deleted successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Category not found"));
    }
}
