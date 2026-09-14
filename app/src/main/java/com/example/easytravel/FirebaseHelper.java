package com.example.easytravel;

import androidx.annotation.NonNull;

import com.example.easytravel.Booking;
import com.example.easytravel.Deal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {

    // ====== Deals ======
    public static DatabaseReference getDealsRef() {
        return FirebaseDatabase.getInstance().getReference("deals");
    }

    public static void getAllDeals(DealsCallback callback) {
        getDealsRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Deal> deals = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    Deal deal = s.getValue(Deal.class);
                    if (deal != null) {
                        deal.setId(s.getKey());
                        deals.add(deal);
                    }
                }
                callback.onLoaded(deals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    // ====== Bookings ======
    public static DatabaseReference getBookingsRef() {
        return FirebaseDatabase.getInstance().getReference("bookings");
    }

    public static void addBooking(Booking booking, SimpleCallback callback) {
        String id = getBookingsRef().push().getKey();
        if (id != null) {
            booking.setId(id);
            getBookingsRef().child(id).setValue(booking)
                    .addOnSuccessListener(aVoid -> callback.onSuccess())
                    .addOnFailureListener(e -> callback.onError(e.getMessage()));
        }
    }

    // ====== 接口 ======
    public interface DealsCallback {
        void onLoaded(List<Deal> deals);
        void onError(String error);
    }

    public interface SimpleCallback {
        void onSuccess();
        void onError(String error);
    }
}