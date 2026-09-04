package com.example.easytravel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ApproveDealsActivity extends AppCompatActivity {

    private Button btnBack, btnApprove1, btnReject1, btnApprove2, btnReject2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_deals);

        btnBack = findViewById(R.id.btnBack);
        btnApprove1 = findViewById(R.id.btnApprove1);
        btnReject1 = findViewById(R.id.btnReject1);
        btnApprove2 = findViewById(R.id.btnApprove2);
        btnReject2 = findViewById(R.id.btnReject2);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ApproveDealsActivity.this, "✅ Pizza Deal Approved!", Toast.LENGTH_SHORT).show();
                v.setEnabled(false);
            }
        });

        btnReject1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ApproveDealsActivity.this, "❌ Pizza Deal Rejected", Toast.LENGTH_SHORT).show();
                v.setEnabled(false);
            }
        });

        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ApproveDealsActivity.this, "✅ Facial Approved!", Toast.LENGTH_SHORT).show();
                v.setEnabled(false);
            }
        });

        btnReject2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ApproveDealsActivity.this, "❌ Facial Rejected", Toast.LENGTH_SHORT).show();
                v.setEnabled(false);
            }
        });
    }
}