package com.example.easytravel;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoRegister;
    private RadioGroup rgRole;
    private RadioButton rbCustomer, rbSupplier, rbAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ========== 找到控件 ==========
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);
        rgRole = findViewById(R.id.rgRole);
        rbCustomer = findViewById(R.id.rbCustomer);
        rbSupplier = findViewById(R.id.rbSupplier);
        rbAdmin = findViewById(R.id.rbAdmin);

        // ========== 自动匹配角色：输入邮箱时自动选中对应的角色 ==========
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().toLowerCase();
                if (email.contains("admin")) {
                    rbAdmin.setChecked(true);
                } else if (email.contains("supplier")) {
                    rbSupplier.setChecked(true);
                } else if (email.contains("customer")) {
                    rbCustomer.setChecked(true);
                }
                // 如果都不包含，保持当前选中的不变
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // ========== 登录按钮 ==========
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 获取选择的角色
                int selectedId = rgRole.getCheckedRadioButtonId();
                String role = "Customer";
                if (selectedId == rbSupplier.getId()) {
                    role = "Supplier";
                } else if (selectedId == rbAdmin.getId()) {
                    role = "Admin";
                }

                // 根据角色跳转到对应的 Dashboard
                Intent intent;
                if (role.equals("Admin")) {
                    intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                } else if (role.equals("Supplier")) {
                    intent = new Intent(LoginActivity.this, SupplierDashboardActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, CustomerDashboardActivity.class);
                }

                intent.putExtra("user_email", email);
                intent.putExtra("user_role", role);
                startActivity(intent);
                finish();
            }
        });

        // ========== 注册按钮 ==========
        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}