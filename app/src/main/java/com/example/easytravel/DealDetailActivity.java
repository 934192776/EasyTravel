package com.example.easytravel;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        deal = (Deal) getIntent().getSerializableExtra("deal_data");

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvDescription = findViewById(R.id.tvDetailDesc);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvSupplier = findViewById(R.id.tvDetailSupplier);
        tvLocation = findViewById(R.id.tvDetailLocation);
        ivImage = findViewById(R.id.ivDetailImage);
        btnBookNow = findViewById(R.id.btnBookNow);

        mDatabase = FirebaseDatabase.getInstance().getReference("bookings");

        if (deal != null) {
            tvTitle.setText(deal.getTitle());
            tvDescription.setText(deal.getDescription());
            tvPrice.setText("$" + deal.getPrice());
            tvCategory.setText(deal.getCategory());
            tvSupplier.setText("Supplier: " + deal.getSupplierName());
            tvLocation.setText("Location: " + deal.getLocation());
            setTitle(deal.getTitle());

            if (deal.getImageUrl() != null && !deal.getImageUrl().isEmpty()) {
                Glide.with(this).load(deal.getImageUrl()).into(ivImage);
            } else {
                ivImage.setImageResource(R.drawable.ic_launcher_foreground);
            }
        }

        // ========== Book Now 按钮 ==========
        btnBookNow.setOnClickListener(v -> {
            if (deal != null) {
                showBookingDialog();
            }
        });
    }

    // ========== 显示输入人数的对话框 ==========
    private void showBookingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Number of People");

        // 创建一个输入框用来输入人数
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText("2"); // 默认填2
        builder.setView(input);

        // 确认按钮
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            String peopleStr = input.getText().toString();
            if (peopleStr.isEmpty()) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
                return;
            }

            int numberOfPeople = Integer.parseInt(peopleStr);
            if (numberOfPeople <= 0) {
                Toast.makeText(this, "Number of people must be at least 1", Toast.LENGTH_SHORT).show();
                return;
            }

            // 计算总价：单价 * 人数
            double pricePerPerson = 0.0;
            try {
                pricePerPerson = Double.parseDouble(deal.getPrice());
            } catch (Exception e) {
                e.printStackTrace();
            }
            double totalPrice = pricePerPerson * numberOfPeople;

            // 保存到 Firebase
            saveBookingToFirebase(numberOfPeople, totalPrice);
        });

        // 取消按钮
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // ========== 将预订数据存入 Firebase ==========
    private void saveBookingToFirebase(int peopleCount, double totalPrice) {
        String bookingId = mDatabase.push().getKey();

        Booking newBooking = new Booking();
        newBooking.setId(bookingId);
        newBooking.setDealId(deal.getId());
        newBooking.setDealTitle(deal.getTitle());
        newBooking.setCustomerName("John Doe"); // 演示用名字
        newBooking.setDate("2026-11-01");
        newBooking.setNumberOfPeople(peopleCount); // 使用用户输入的人数
        newBooking.setTotalPrice(totalPrice);      // 使用计算出的总价
        newBooking.setStatus("PENDING");
        newBooking.setTimestamp(System.currentTimeMillis());

        if (bookingId != null) {
            mDatabase.child(bookingId).setValue(newBooking)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(DealDetailActivity.this,
                                "Booking Successful!\n" + peopleCount + " people - Total: $" + totalPrice,
                                Toast.LENGTH_LONG).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(DealDetailActivity.this, "Booking Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}