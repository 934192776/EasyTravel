package com.example.easytravel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.easytravel.model.Deal;

public class DealDetailActivity extends AppCompatActivity {

    private Deal deal;
    private TextView tvTitle, tvDescription, tvPrice, tvCategory, tvSupplier, tvLocation;
    private ImageView ivImage;
    private Button btnBookNow;

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

        // ========== 显示数据 ==========
        if (deal != null) {
            tvTitle.setText(deal.getTitle());
            tvDescription.setText(deal.getDescription());
            tvPrice.setText("$" + deal.getPrice());
            tvCategory.setText("Category: " + deal.getCategory());
            tvSupplier.setText("Supplier: " + deal.getSupplier());
            tvLocation.setText("Location: " + deal.getLocation());
            setTitle(deal.getTitle());

            // 加载图片
            if (deal.getImageUrl() != null && !deal.getImageUrl().isEmpty()) {
                // 后续用 Glide 加载
            } else {
                ivImage.setImageResource(R.drawable.ic_launcher_foreground);
            }
        }

        // ========== Book Now 按钮 ==========
        btnBookNow.setOnClickListener(v -> {
            Toast.makeText(this, "Booking successful!", Toast.LENGTH_LONG).show();
            // TODO: 跳转到预订页面
        });
    }
}