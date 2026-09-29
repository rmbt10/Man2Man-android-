package com.example.man2manwebapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.man2manwebapp.adapter.CartAdapter;
import com.example.man2manwebapp.model.CartItem;
import com.example.man2manwebapp.utils.CartManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotal;
    private List<CartItem> cartItems;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your Cart");

        recyclerView = findViewById(R.id.recycler_cart);
        tvTotal = findViewById(R.id.tv_total);
        Button btnCheckout = findViewById(R.id.btn_checkout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItems = CartManager.getCart(this);
        adapter = new CartAdapter(this, cartItems, this::updateTotal);
        recyclerView.setAdapter(adapter);

        updateTotal();

        if (cartItems.isEmpty()) {
            findViewById(R.id.layout_empty).setVisibility(View.VISIBLE);
            findViewById(R.id.recycler_cart).setVisibility(View.GONE);
            findViewById(R.id.bottom_bar).setVisibility(View.GONE);

            Button btnShopNow = findViewById(R.id.btn_shop_now);
            btnShopNow.setOnClickListener(v -> {
                startActivity(new Intent(CartActivity.this, ProductsActivity.class));
                finish();
            });
        } else {
            findViewById(R.id.layout_empty).setVisibility(View.GONE);
            findViewById(R.id.recycler_cart).setVisibility(View.VISIBLE);
            findViewById(R.id.bottom_bar).setVisibility(View.VISIBLE);
        }

        btnCheckout.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        // Swipe to remove
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                CartItem removedItem = cartItems.get(position);
                cartItems.remove(position);
                adapter.notifyItemRemoved(position);
                CartManager.saveCart(CartActivity.this, cartItems);
                updateTotal();

                Snackbar.make(recyclerView, "Item removed", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> {
                            cartItems.add(position, removedItem);
                            adapter.notifyItemInserted(position);
                            CartManager.saveCart(CartActivity.this, cartItems);
                            updateTotal();
                        }).show();
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    private void updateTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        tvTotal.setText("Total: $" + String.format("%.2f", total));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
    }
}