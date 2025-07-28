package com.example.ecommercesys.Service;

import com.example.ecommercesys.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    //This class has been reviewed.

    private ArrayList<Category> categories = new ArrayList<>();


    public ArrayList<Category> getCategories() {
        return categories;
    }


    public void addCategory(Category category) {
        categories.add(category);
    }


    public boolean updateCategory(String id, Category category) {
        for (Category c : categories) {
            if (c.getId().equals(id)) {
                categories.set(categories.indexOf(c), category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id) {
        for (Category c : categories) {
            if ((c.getId()).equals(id)) {
                categories.remove(c);
                return true;
            }
        }
        return false;
    }
}
