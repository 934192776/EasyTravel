package com.example.easytravel;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhone, tvMemberSince;
    private Button btnBack, btnEditName;
    private DatabaseReference mDatabase;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 找到控件
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvMemberSince = findViewById(R.id.tvMemberSince);
        btnBack = findViewById(R.id.btnBack);
        btnEditName = findViewById(R.id.btnEditName);

        // 获取传递的数据
        userEmail = getIntent().getStringExtra("user_email");
        if (userEmail == null) userEmail = "john@example.com";

        tvName.setText("John Doe");
        tvEmail.setText("📧 " + userEmail);
        tvPhone.setText("📱 +64 21 123 4567");
        tvMemberSince.setText("📅 Member since: 2024");

        // 初始化 Firebase
        // 替换邮箱中的 '.' 防止 Firebase 报错
        String safeEmailKey = userEmail.replace(".", "_");
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Update 操作：修改名字
        btnEditName.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Edit Your Name");

            final EditText input = new EditText(this);
            input.setText(tvName.getText().toString());
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> {
                String newName = input.getText().toString();

                // 1. 更新本地 UI
                tvName.setText(newName);

                // 2. 更新到 Firebase
                mDatabase.child(safeEmailKey).child("name").setValue(newName)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(ProfileActivity.this, "Database Updated!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(ProfileActivity.this, "Update Failed", Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        btnBack.setOnClickListener(v -> finish());
    }
}