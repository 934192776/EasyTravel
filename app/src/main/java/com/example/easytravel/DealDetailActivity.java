package com.example.easytravel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class DealDetailActivity extends AppCompatActivity {

    private Deal deal;
    private TextView tvTitle, tvDescription, tvPrice, tvCategory, tvSupplier, tvLocation;
    private ImageView ivImage;
    private Button btnBookNow;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detail);

        // ========== 获取数据 ==========
        deal = (Deal) getIntent().getSerializableExtra("deal_data");

        // ========== 找到控件 ==========
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvDescription = findViewById(R.id.tvDetailDesc);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvSupplier = findViewById(R.id.tvDetailSupplier);
        tvLocation = findViewById(R.id.tvDetailLocation);
        ivImage = findViewById(R.id.ivDetailImage);
        btnBookNow = findViewById(R.id.btnBookNow);

        // ========== 初始化 Firebase ==========
        mDatabase = FirebaseDatabase.getInstance().getReference("bookings");

        // ========== 显示数据 ==========
        if (deal != null) {
            tvTitle.setText(deal.getTitle());
            tvDescription.setText(deal.getDescription());
            tvPrice.setText("$" + deal.getPrice());
            tvCategory.setText("Category: " + deal.getCategory());
            tvSupplier.setText("Supplier: " + deal.getSupplier());
            tvLocation.setText("Location: " + deal.getLocation());
            setTitle(deal.getTitle());

            ivImage.setImageResource(R.drawable.ic_launcher_foreground);
        }

        // ========== Create 操作：点击预订 ==========
        btnBookNow.setOnClickListener(v -> {
            if (deal != null) {
                // 生成一个唯一的预订 ID
                String bookingId = mDatabase.push().getKey();

                // 组装要存入数据库的数据
                Map<String, Object> bookingData = new HashMap<>();
                bookingData.put("bookingId", bookingId);
                bookingData.put("dealId", deal.getId());
                bookingData.put("title", deal.getTitle());
                bookingData.put("price", deal.getPrice());
                bookingData.put("date", "2026-10-25"); // 实际开发中可以选日期
                bookingData.put("status", "Confirmed");

                // 写入 Firebase 数据库
                if (bookingId != null) {
                    mDatabase.child(bookingId).setValue(bookingData)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(DealDetailActivity.this, "Booking Successful! Saved to Database.", Toast.LENGTH_LONG).show();
                                finish(); // 预订成功后关闭页面
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(DealDetailActivity.this, "Booking Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });
    }
}