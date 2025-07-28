package com.example.ecommercesys.Service;

import com.example.ecommercesys.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    //This class has been reviewed.


   private ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }


    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }


    public boolean updateMerchant(String id, Merchant merchant) {
        for (Merchant m : merchants) {
            if (m.getId().equals(id)) {
                merchants.set(merchants.indexOf(m), merchant);
                return true;
            }
        }
        return false;
    }


    public boolean deleteMerchant(String id) {
        for (Merchant m : merchants) {
            if (m.getId().equals(id)) {
                merchants.remove(m);
                return true;
            }
        }
        return false;
    }
}

