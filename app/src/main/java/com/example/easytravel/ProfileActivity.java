package com.example.easytravel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhone, tvMemberSince;
    private Button btnBack, btnUploadAvatar;
    private ImageView ivAvatar;
    private DatabaseReference mDatabase;
    private String userEmail;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        Glide.with(this).load(selectedImageUri).into(ivAvatar);
                        uploadImageToCloudinary(selectedImageUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvMemberSince = findViewById(R.id.tvMemberSince);
        btnBack = findViewById(R.id.btnBack);
        btnUploadAvatar = findViewById(R.id.btnUploadAvatar);
        ivAvatar = findViewById(R.id.ivAvatar);

        userEmail = getIntent().getStringExtra("user_email");
        if (userEmail == null) userEmail = "john@example.com";

        tvEmail.setText("📧 " + userEmail);
        tvMemberSince.setText("📅 Member since: 2024");

        String safeEmailKey = userEmail.replace(".", "_");
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(safeEmailKey);

        // ========== 1. 从 Firebase 读取名字和电话 ==========
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String realName = snapshot.child("name").getValue(String.class);
                    String realPhone = snapshot.child("phone").getValue(String.class);
                    String avatarUrl = snapshot.child("avatarUrl").getValue(String.class);

                    tvName.setText(realName != null && !realName.isEmpty() ? realName : "Jing Hui");
                    tvPhone.setText(realPhone != null && !realPhone.isEmpty() ? "📱 " + realPhone : "📱 +64 21 123 4567");

                    if (avatarUrl != null && !avatarUrl.isEmpty()) {
                        Glide.with(ProfileActivity.this).load(avatarUrl).into(ivAvatar);
                    } else {
                        ivAvatar.setImageResource(R.drawable.ic_launcher_foreground);
                    }
                } else {
                    // 数据库没有该用户节点的默认显示
                    tvName.setText("Jing Hui");
                    tvPhone.setText("📱 +64 21 123 4567");
                    ivAvatar.setImageResource(R.drawable.ic_launcher_foreground);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // ========== 2. 点击名字进行修改 (Update Name) ==========
        tvName.setOnClickListener(v -> showEditDialog("name", tvName.getText().toString(), tvName, "Name"));

        // ========== 3. 点击电话进行修改 (Update Phone) ==========
        tvPhone.setOnClickListener(v -> {
            // 去掉前面的 emoji 前缀
            String currentPhone = tvPhone.getText().toString().replace("📱 ", "");
            showEditDialog("phone", currentPhone, tvPhone, "Phone Number");
        });

        // 上传头像
        btnUploadAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        btnBack.setOnClickListener(v -> finish());
    }

    // ========== 弹窗修改数据的通用方法 ==========
    private void showEditDialog(String dbField, String currentValue, TextView textView, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + title);

        final EditText input = new EditText(this);
        input.setText(currentValue);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newValue = input.getText().toString().trim();
            if (!newValue.isEmpty()) {
                // 更新 Firebase 数据库 (Update 操作)
                mDatabase.child(dbField).setValue(newValue)
                        .addOnSuccessListener(aVoid -> {
                            // UI 会通过上面的 ValueEventListener 自动更新，但为了体验，也可以手动 setText
                            if (dbField.equals("phone")) {
                                textView.setText("📱 " + newValue);
                            } else {
                                textView.setText(newValue);
                            }
                            Toast.makeText(ProfileActivity.this, title + " updated in Database!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show());
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void uploadImageToCloudinary(Uri imageUri) {
        MediaManager.get().upload(imageUri)
                .unsigned("photos")
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Toast.makeText(ProfileActivity.this, "Uploading...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {}

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String secureUrl = (String) resultData.get("secure_url");
                        Toast.makeText(ProfileActivity.this, "Upload Successful!", Toast.LENGTH_SHORT).show();

                        if (secureUrl != null) {
                            mDatabase.child("avatarUrl").setValue(secureUrl)
                                    .addOnSuccessListener(aVoid ->
                                            Toast.makeText(ProfileActivity.this, "Avatar saved!", Toast.LENGTH_SHORT).show()
                                    );
                        }
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Toast.makeText(ProfileActivity.this, "Failed: " + error.getDescription(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {}
                }).dispatch();
    }
}