package com.example.man2manwebapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBrowse = findViewById(R.id.btn_browse);
        Button btnSignIn = findViewById(R.id.btn_signin);
        TextView tvSignup = findViewById(R.id.tv_signup);

        btnBrowse.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProductsActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        tvSignup.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }
}