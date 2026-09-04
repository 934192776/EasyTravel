package com.example.easytravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SupplierDashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnFeature1, btnFeature2, btnFeature3, btnGoHome, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnFeature1 = findViewById(R.id.btnFeature1);
        btnFeature2 = findViewById(R.id.btnFeature2);
        btnFeature3 = findViewById(R.id.btnFeature3);
        btnGoHome = findViewById(R.id.btnGoHome);
        btnLogout = findViewById(R.id.btnLogout);

        final String email = getIntent().getStringExtra("user_email");
        String displayEmail = (email == null) ? "Supplier" : email;
        tvWelcome.setText("📦 Welcome, " + displayEmail + "!");

        // ========== 功能1：发布新Deal → 跳转到 AddDealActivity ==========
        btnFeature1.setText("➕ Add New Deal");
        btnFeature1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboardActivity.this, AddDealActivity.class);
                startActivity(intent);
            }
        });

        // ========== 功能2：我的Deal列表 → 跳转到 SupplierDealsActivity ==========
        btnFeature2.setText("📋 My Deals List");
        btnFeature2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboardActivity.this, SupplierDealsActivity.class);
                startActivity(intent);
            }
        });

        // ========== 功能3：查看收入 → 跳转到 SupplierEarningsActivity ==========
        btnFeature3.setText("💰 View Earnings");
        btnFeature3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboardActivity.this, SupplierEarningsActivity.class);
                startActivity(intent);
            }
        });

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboardActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SupplierDashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SupplierDashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}