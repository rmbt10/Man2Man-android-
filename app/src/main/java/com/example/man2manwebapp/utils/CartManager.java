package com.example.man2manwebapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.man2manwebapp.model.CartItem;
import com.example.man2manwebapp.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static final String PREF_NAME = "cart_prefs";
    private static final String KEY_CART = "cart_items";
    private static final Gson gson = new Gson();

    public static void addToCart(Context context, Product product, String size, int quantity) {
        List<CartItem> cart = getCart(context);
        boolean found = false;

        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(product.getId()) && item.getSize().equals(size)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItem(product, size, quantity));
        }

        saveCart(context, cart);
    }

    public static List<CartItem> getCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_CART, null);
        if (json == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<CartItem>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void saveCart(Context context, List<CartItem> cart) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_CART, gson.toJson(cart));
        editor.apply();
    }

    public static void clearCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_CART).apply();
    }
}