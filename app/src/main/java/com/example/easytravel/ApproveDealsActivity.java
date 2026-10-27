
package com.example.easytravel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ApproveDealsActivity extends AppCompatActivity {

    private LinearLayout dealsContainer;
    private TextView tvStatus;
    private Button btnRefreshDeals;

    private DatabaseReference dealsRef;
    private ValueEventListener dealsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_deals);

        dealsContainer = findViewById(R.id.dealsContainer);
        tvStatus = findViewById(R.id.tvStatus);
        btnRefreshDeals = findViewById(R.id.btnRefreshDeals);

        dealsRef = FirebaseDatabase.getInstance().getReference("deals");

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        btnRefreshDeals.setOnClickListener(v -> loadDeals());

        loadDeals();
    }

    private void loadDeals() {
        tvStatus.setText("Loading deals...");
        dealsContainer.removeAllViews();

        if (dealsListener != null) {
            dealsRef.removeEventListener(dealsListener);
        }

        dealsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                dealsContainer.removeAllViews();

                if (!snapshot.exists()) {
                    tvStatus.setText("No deals found.");
                    return;
                }

                int count = 0;

                for (DataSnapshot deal : snapshot.getChildren()) {
                    String dealId = deal.getKey();

                    String title = deal.child("title").getValue(String.class);
                    String supplier = deal.child("supplier").getValue(String.class);
                    String category = deal.child("category").getValue(String.class);
                    String status = deal.child("status").getValue(String.class);

                    if (title == null || title.trim().isEmpty()) {
                        title = "Untitled deal";
                    }

                    if (supplier == null || supplier.trim().isEmpty()) {
                        supplier = "Unknown supplier";
                    }

                    if (category == null || category.trim().isEmpty()) {
                        category = "Uncategorized";
                    }

                    // Do not silently classify existing deals as pending.
                    if (status == null || status.trim().isEmpty()) {
                        status = "Not reviewed";
                    }

                    addDealCard(
                            dealId,
                            title,
                            supplier,
                            category,
                            status
                    );

                    count++;
                }

                tvStatus.setText(count + " deal(s) found.");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                tvStatus.setText("Unable to load deals.");
                Toast.makeText(
                        ApproveDealsActivity.this,
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        };

        dealsRef.addValueEventListener(dealsListener);
    }

    private void addDealCard(
            String dealId,
            String title,
            String supplier,
            String category,
            String status
    ) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(28, 24, 28, 24);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(0, 0, 0, 24);
        card.setLayoutParams(cardParams);
        card.setBackgroundResource(
                android.R.drawable.dialog_holo_light_frame
        );

        TextView details = new TextView(this);
        details.setText(
                title + "\n"
                        + "Supplier: " + supplier + "\n"
                        + "Category: " + category + "\n"
                        + "Deal ID: " + dealId + "\n"
                        + "Status: " + status
        );
        details.setTextSize(16);
        details.setPadding(0, 0, 0, 16);
        card.addView(details);

        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);

        Button approveButton = new Button(this);
        approveButton.setText("Approve");

        Button rejectButton = new Button(this);
        rejectButton.setText("Reject");

        buttons.addView(approveButton,
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                ));

        buttons.addView(rejectButton,
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                ));

        card.addView(buttons);
        dealsContainer.addView(card);

        boolean approved = "Approved".equalsIgnoreCase(status);
        boolean rejected = "Rejected".equalsIgnoreCase(status);

        // Keep the current decision visible and prevent repeated decisions.
        if (approved || rejected) {
            approveButton.setEnabled(false);
            rejectButton.setEnabled(false);
        }

        approveButton.setOnClickListener(v ->
                updateDealStatus(dealId, "Approved", true));

        rejectButton.setOnClickListener(v ->
                updateDealStatus(dealId, "Rejected", false));
    }

    private void updateDealStatus(
            String dealId,
            String newStatus,
            boolean available
    ) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", newStatus);
        updates.put("available", available);

        dealsRef.child(dealId).updateChildren(updates)
                .addOnSuccessListener(unused ->
                        Toast.makeText(
                                this,
                                "Deal " + newStatus.toLowerCase(Locale.ROOT)
                                        + " successfully.",
                                Toast.LENGTH_SHORT
                        ).show())
                .addOnFailureListener(error ->
                        Toast.makeText(
                                this,
                                "Update failed: " + error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dealsListener != null && dealsRef != null) {
            dealsRef.removeEventListener(dealsListener);
        }
    }
}
