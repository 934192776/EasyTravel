package com.example.easytravel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhone, tvMemberSince;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvMemberSince = findViewById(R.id.tvMemberSince);
        btnBack = findViewById(R.id.btnBack);

        // 获取传递的数据
        String email = getIntent().getStringExtra("user_email");
        if (email == null) email = "john@example.com";

        tvName.setText("John Doe");
        tvEmail.setText("📧 " + email);
        tvPhone.setText("📱 +64 21 123 4567");
        tvMemberSince.setText("📅 Member since: 2024");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}