package com.example.ecommercesys.Service;

import com.example.ecommercesys.Model.Merchant;
import com.example.ecommercesys.Model.MerchantStock;
import com.example.ecommercesys.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    private final ProductService productService;
    private final MerchantService merchantService;

    private ArrayList<MerchantStock> stocks = new ArrayList<>();

    public ArrayList<MerchantStock> getStocks() {
        return stocks;
    }


    public void addStock(MerchantStock stock) {
        stocks.add(stock);
    }


    public boolean updateStock(String id, MerchantStock stock) {
        for (MerchantStock m : stocks) {
            if (m.getId().equals(id)) {
                stocks.set(stocks.indexOf(m), stock);
                return true;
            }
        }
        return false;
    }


    public boolean deleteStock(String id) {
        for (MerchantStock m : stocks) {
            if (m.getId().equals(id)) {
                stocks.remove(m);
                return true;
            }
        }
        return false;
    }

    //This method has been reviewed.
    public boolean addStockToMerchant(String productId, String merchantId, int amount) {

        boolean productExists = false;
        for (Product p : productService.getProducts()) {
            if (p.getId().equals(productId)) {
                productExists = true;
                break;
            }
        }
        if (!productExists) return false;

        boolean merchantExists = false;
        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantId)) {
                merchantExists = true;
                break;
            }
        }
        if (!merchantExists) return false;

        for (MerchantStock stock : stocks) {
            if (stock.getProductId().equals(productId) && stock.getMerchantId().equals(merchantId)) {
                stock.setStock(stock.getStock() + amount);
                return true;
            }
        }

        return false;
    }





                            // =============================================================
//                                                  First_End_Point
                            // =============================================================

    public ArrayList<Merchant> getAvailableMerchantsForProduct(String productId) {

        ArrayList<Merchant> availableMerchants = new ArrayList<>();

        for (MerchantStock stock : stocks) {
            if (stock.getProductId().equals(productId) && stock.getStock() > 0) {
                for (Merchant merchant : merchantService.getMerchants()) {
                    if (merchant.getId().equals(stock.getMerchantId())) {
                        availableMerchants.add(merchant);
                        break;
                    }
                }
            }
        }

        return availableMerchants;
    }

                             // =============================================================
                             // =============================================================




                            // =============================================================
//                                                  Third_End_Point
                            // =============================================================
    public ArrayList<Product> getProductsBelowStockThreshold(int threshold,String merchantId) {
        ArrayList<Product> lowStockProducts = new ArrayList<>();

        for (MerchantStock stock : stocks) {
            if (stock.getStock() < threshold) {
                for (Product product : productService.getProducts()) {
                    if (product.getId().equals(stock.getProductId()) && !lowStockProducts.contains(product)
                        stock.getMerchantId().equals(merchantId)) {
                        lowStockProducts.add(product);
                    }
                }
            }
        }

        return lowStockProducts;
    }
                            // =============================================================
                            // =============================================================


}
