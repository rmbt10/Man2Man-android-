package com.example.man2manwebapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.man2manwebapp.model.Product;
import com.example.man2manwebapp.utils.CartManager;
import com.example.man2manwebapp.utils.MockData;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;
    private String selectedSize = "M";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Jersey Detail");

        ImageView imgProduct = findViewById(R.id.img_product_detail);
        TextView tvName = findViewById(R.id.tv_name_detail);
        TextView tvClubSeason = findViewById(R.id.tv_club_season_detail);
        TextView tvPrice = findViewById(R.id.tv_price_detail);
        RadioGroup radioGroupSize = findViewById(R.id.radio_group_size);
        Button btnAddToCart = findViewById(R.id.btn_add_to_cart);
        Button btnViewCart = findViewById(R.id.btn_view_cart);

        String productId = getIntent().getStringExtra("product_id");

        product = null;
        for (Product p : MockData.getProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        if (product != null) {
            imgProduct.setImageResource(product.getImageResId());
            tvName.setText(product.getName());
            tvClubSeason.setText(product.getClub() + " - " + product.getSeason());
            tvPrice.setText("$" + String.format("%.2f", product.getPrice()));

            radioGroupSize.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == R.id.rb_s) selectedSize = "S";
                else if (checkedId == R.id.rb_m) selectedSize = "M";
                else if (checkedId == R.id.rb_l) selectedSize = "L";
                else if (checkedId == R.id.rb_xl) selectedSize = "XL";
            });

            btnAddToCart.setOnClickListener(v -> {
                CartManager.addToCart(this, product, selectedSize, 1);
                Toast.makeText(this, product.getName() + " (Size " + selectedSize + ") added!", Toast.LENGTH_SHORT).show();
            });

            btnViewCart.setOnClickListener(v -> {
                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            });
        } else {
            tvName.setText("Product Not Found");
        }
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