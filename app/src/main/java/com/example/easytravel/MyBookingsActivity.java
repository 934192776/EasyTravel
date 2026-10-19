package com.example.easytravel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyBookingsActivity extends AppCompatActivity {

    private Button btnBack, btnCancelBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        btnBack = findViewById(R.id.btnBack);

        // 动态添加一个取消预订的按钮用于演示 (Delete 操作)
        btnCancelBooking = new Button(this);
        btnCancelBooking.setText("取消预订 (Cancel Booking)");
        // 假设你的 activity_my_bookings.xml 最外层是 LinearLayout，你需要找到它把按钮加进去
        // LinearLayout rootLayout = findViewById(R.id.root_layout); // 假设你在XML里加了id
        // rootLayout.addView(btnCancelBooking);

        btnBack.setOnClickListener(v -> finish());

        btnCancelBooking.setOnClickListener(v -> {
            // 假设要删除订单 ID 为 "booking_123" 的记录
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("bookings").child("booking_123");

            ref.removeValue().addOnSuccessListener(aVoid -> {
                Toast.makeText(MyBookingsActivity.this, "预订已取消 (Deleted from Firebase)!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(MyBookingsActivity.this, "取消失败", Toast.LENGTH_SHORT).show();
            });
        });
    }
}