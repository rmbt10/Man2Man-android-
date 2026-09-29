package com.example.man2manwebapp.utils;

import com.example.man2manwebapp.model.Product;

import java.util.ArrayList;
import java.util.List;
import com.example.man2manwebapp.R;

public class MockData {

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("1", "Liverpool Away Jersey", 89.99,
                R.drawable.liverpool_away,  // ← Your Liverpool image
                "Liverpool", "2023/24", "New"));

        products.add(new Product("2", "Manchester United Home", 94.99,
                R.drawable.man_utd_home,  // ← Your Man Utd image
                "Manchester United", "2023/24", "New"));

        products.add(new Product("3", "Real Madrid Classic", 99.99,
                R.drawable.real_madrid_classic,  // ← Your Real Madrid image
                "Real Madrid", "Classic", "Excellent"));

        // Add more if you have
        products.add(new Product("4", "Barcelona Home Jersey", 89.99,
                R.drawable.barcelona_home,
                "Barcelona", "2023/24", "New"));

        return products;
    }
}