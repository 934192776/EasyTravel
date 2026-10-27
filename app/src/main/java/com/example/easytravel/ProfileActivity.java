package com.example.easytravel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
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
                        // 预览选中的图片
                        Glide.with(this).load(selectedImageUri).into(ivAvatar);
                        // 上传
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

        tvName.setText("John Doe");
        tvEmail.setText("📧 " + userEmail);
        tvPhone.setText("📱 +64 21 123 4567");
        tvMemberSince.setText("📅 Member since: 2024");

        String safeEmailKey = userEmail.replace(".", "_");
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(safeEmailKey);

        // 从 Firebase 读取已保存的头像
        mDatabase.child("avatarUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String avatarUrl = snapshot.getValue(String.class);
                if (avatarUrl != null && !avatarUrl.isEmpty()) {
                    Glide.with(ProfileActivity.this).load(avatarUrl).into(ivAvatar);
                } else {
                    // 如果没有头像，显示默认的机器人图标
                    ivAvatar.setImageResource(R.drawable.ic_launcher_foreground);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        btnUploadAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        btnBack.setOnClickListener(v -> finish());
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
                                            Toast.makeText(ProfileActivity.this, "Saved to Database!", Toast.LENGTH_SHORT).show()
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