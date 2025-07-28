package com.example.ecommercesys.Service;

import com.example.ecommercesys.Model.Merchant;
import com.example.ecommercesys.Model.MerchantStock;
import com.example.ecommercesys.Model.Product;
import com.example.ecommercesys.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {


    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    private String[][] productSales = new String[100][2]; // [productId, count]
    private int salesIndex = 0;


    private ArrayList<User> users = new ArrayList<>();

    private ArrayList<String> purchaseHistory = new ArrayList<>();


    public ArrayList<User> getUsers() {
        return users;
    }


    public void addUser(User user) {
        users.add(user);
    }


    public boolean updateUser(String id, User user) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                users.set(users.indexOf(u), user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                users.remove(u);
                return true;
            }
        }
        return false;
    }




    //This method has been reviewed.
    public boolean buyProduct(String userId, String productId, String merchantId) {


        User targetUser = null;
        for (User u : users) {
            if (u.getId().equals(userId)) {
                targetUser = u;
                break;
            }
        }
        if (targetUser == null) return false;


        Product targetProduct = null;
        for (Product p : productService.getProducts()) {
            if (p.getId().equals(productId)) {
                targetProduct = p;
                break;
            }
        }
        if (targetProduct == null) return false;


        boolean merchantExists = false;
        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantId)) {
                merchantExists = true;
                break;
            }
        }
        if (!merchantExists) return false;


        for (MerchantStock stock : merchantStockService.getStocks()) {
            if (stock.getProductId().equals(productId) && stock.getMerchantId().equals(merchantId)) {
                if (stock.getStock() > 0 && targetUser.getBalance() >= targetProduct.getPrice()) {
                    stock.setStock(stock.getStock() - 1);
                    targetUser.setBalance(targetUser.getBalance() - targetProduct.getPrice());




                          // =============================================================
//                                                  Second_End_Point
                          // =============================================================



                    purchaseHistory.add("User [" + userId + "] bought product [" + productId + "] from merchant [" + merchantId + "]");

                          // =============================================================
                         // =============================================================




                     // =============================================================
//                                                  Fourth_End_Point
                     // =============================================================
                    boolean found = false;
                    for (int i = 0; i < salesIndex; i++) {
                        if (productSales[i][0].equals(productId)) {
                            int count = Integer.parseInt(productSales[i][1]);
                            productSales[i][1] = String.valueOf(count + 1);
                            found = true;
                            break;
                        }
                    }
                    if (!found && salesIndex < productSales.length) {
                        productSales[salesIndex][0] = productId;
                        productSales[salesIndex][1] = "1";
                        salesIndex++;
                    }


                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

                        // =============================================================
//                                                  Second_End_Point
                        // =============================================================

    public ArrayList<String> getPurchaseHistory() {
        return purchaseHistory;
    }

                        // =============================================================
                       // =============================================================




                         // =============================================================
//                                                  Fourth_End_Point
                       // =============================================================

    public ArrayList<String> getProductSalesSummary() {
        ArrayList<String> summary = new ArrayList<>();

        for (int i = 0; i < salesIndex; i++) {
            String productId = productSales[i][0];
            int count = Integer.parseInt(productSales[i][1]);

            Product product = null;
            for (Product p : productService.getProducts()) {
                if (p.getId().equals(productId)) {
                    product = p;
                    break;
                }
            }

            if (product != null) {
                double revenue = count * product.getPrice();
                summary.add("Product [" + productId + "] - Sold: " + count + " times - Revenue: " + revenue + " SAR");
            }
        }

        return summary;
    }

    //************************************************************************************



                      // =============================================================
//                                                  Fifth_End_Point
                      // =============================================================


    public ArrayList<Product> getProductsWithinUserBudget(String userId) {
        User targetUser = null;
        for (User user : users) {
            if (user.getId().equals(userId)) {
                targetUser = user;
                break;
            }
        }

        if (targetUser == null) return null;

        ArrayList<Product> affordableProducts = new ArrayList<>();
        for (Product product : productService.getProducts()) {
            if (product.getPrice() <= targetUser.getBalance()) {
                affordableProducts.add(product);
            }
        }

        return affordableProducts;
    }

    //************************************************************************************




}
