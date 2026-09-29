package com.example.man2manwebapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.man2manwebapp.model.CartItem;
import com.example.man2manwebapp.utils.CartManager;

import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Checkout");

        TextView tvTotal = findViewById(R.id.tv_total);
        EditText etName = findViewById(R.id.et_name);
        EditText etAddress = findViewById(R.id.et_address);
        EditText etCity = findViewById(R.id.et_city);
        EditText etCard = findViewById(R.id.et_card_number);
        Button btnConfirm = findViewById(R.id.btn_confirm);

        final double total = calculateTotal();

        if (total == 0) {
            return;
        }

        tvTotal.setText(getString(R.string.total_format, String.format(Locale.US, "%.2f", total)));

        btnConfirm.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String city = etCity.getText().toString().trim();
            String card = etCard.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || city.isEmpty() || card.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Purchase Successful")
                    .setMessage(getString(R.string.purchase_success, String.format(Locale.US, "%.2f", total)))
                    .setPositiveButton("OK", (dialog, which) -> {
                        CartManager.clearCart(this);
                        startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
                        finishAffinity();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    })
                    .setCancelable(false)
                    .show();
        });
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

    private double calculateTotal() {
        double total = 0;
        List<CartItem> cart = CartManager.getCart(this);
        if (cart.isEmpty()) {
            Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            finish();
            return 0;
        }
        for (CartItem item : cart) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
}