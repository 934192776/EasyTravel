package com.example.easytravel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBookings;
    private BookingAdapter bookingAdapter;
    private List<Booking> bookingList = new ArrayList<>();
    private Button btnBack;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        recyclerViewBookings = findViewById(R.id.recyclerViewBookings);
        btnBack = findViewById(R.id.btnBack);

        // 设置 RecyclerView
        recyclerViewBookings.setLayoutManager(new LinearLayoutManager(this));
        bookingAdapter = new BookingAdapter();
        recyclerViewBookings.setAdapter(bookingAdapter);

        btnBack.setOnClickListener(v -> finish());

        // ========== 从 Firebase 读取预订 (Read 操作) ==========
        mDatabase = FirebaseDatabase.getInstance().getReference("bookings");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Booking booking = dataSnapshot.getValue(Booking.class);
                    if (booking != null) {
                        // 必须把 Firebase 的 Key 赋给 ID，删除时才找得到
                        booking.setId(dataSnapshot.getKey());
                        bookingList.add(booking);
                    }
                }
                bookingAdapter.setBookings(bookingList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyBookingsActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        // ========== 处理“取消预订” (Delete 操作) ==========
        bookingAdapter.setOnBookingClickListener(new BookingAdapter.OnBookingClickListener() {
            @Override
            public void onCancelClick(Booking booking) {
                if (booking.getId() != null) {
                    mDatabase.child(booking.getId()).removeValue()
                            .addOnSuccessListener(aVoid -> Toast.makeText(MyBookingsActivity.this, "Booking Cancelled", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(MyBookingsActivity.this, "Error cancelling", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}