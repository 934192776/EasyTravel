
        package com.example.easytravel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminDashboardActivity extends AppCompatActivity {

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String displayEmail = user != null && user.getEmail() != null
                ? user.getEmail()
                : "Admin";

        tvWelcome.setText("Welcome, " + displayEmail + "!");

        btnFeature1.setText("Manage Users");
        btnFeature1.setOnClickListener(v ->
                startActivity(new Intent(
                        AdminDashboardActivity.this,
                        ManageUsersActivity.class
                ))
        );

        btnFeature2.setText("Approve Deals");
        btnFeature2.setOnClickListener(v ->
                startActivity(new Intent(
                        AdminDashboardActivity.this,
                        ApproveDealsActivity.class
                ))
        );

        btnFeature3.setText("View Reports");
        btnFeature3.setOnClickListener(v ->
                startActivity(new Intent(
                        AdminDashboardActivity.this,
                        AdminReportsActivity.class
                ))
        );

        btnGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AdminDashboardActivity.this,
                    HomeActivity.class
            );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(
                    AdminDashboardActivity.this,
                    LoginActivity.class
            );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });
    }
}