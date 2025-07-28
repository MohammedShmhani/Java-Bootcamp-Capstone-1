package com.example.ecommercesys.Service;

import com.example.ecommercesys.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    //This class has been reviewed.

    private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean updateProduct(String id, Product product) {
        for (Product p : products) {
            if ((p.getId()).equals(id)) {
                products.set(products.indexOf(p), product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        for (Product p : products) {
            if ((p.getId()).equals(id)) {
                products.remove(p);
                return true;
            }
        }
        return false;
    }


}
