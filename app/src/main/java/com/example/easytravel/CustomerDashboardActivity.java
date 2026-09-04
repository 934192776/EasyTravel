package com.example.easytravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerDashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnFeature1, btnFeature2, btnFeature3, btnGoHome, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // ========== 找到控件 ==========
        tvWelcome = findViewById(R.id.tvWelcome);
        btnFeature1 = findViewById(R.id.btnFeature1);
        btnFeature2 = findViewById(R.id.btnFeature2);
        btnFeature3 = findViewById(R.id.btnFeature3);
        btnGoHome = findViewById(R.id.btnGoHome);
        btnLogout = findViewById(R.id.btnLogout);

        // ========== 获取并保存 email ==========
        final String email = getIntent().getStringExtra("user_email");
        String displayEmail = (email == null) ? "Customer" : email;
        tvWelcome.setText("👤 Welcome, " + displayEmail + "!");

        // ========== 功能1：我的预订 → 跳转到新页面 ==========
        btnFeature1.setText("📅 My Bookings");
        btnFeature1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboardActivity.this, MyBookingsActivity.class);
                startActivity(intent);
            }
        });

        // ========== 功能2：浏览Deal → 跳转到首页 ==========
        btnFeature2.setText("🔍 Browse Deals");
        btnFeature2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboardActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // ========== 功能3：个人资料 → 跳转到新页面 ==========
        btnFeature3.setText("👤 My Profile");
        btnFeature3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboardActivity.this, ProfileActivity.class);
                intent.putExtra("user_email", email);
                startActivity(intent);
            }
        });

        // ========== 返回首页 ==========
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboardActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        // ========== 退出登录 ==========
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerDashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CustomerDashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}