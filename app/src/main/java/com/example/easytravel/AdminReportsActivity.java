
package com.example.easytravel;

import android.os.Bundle;
import android.view.Gravity;
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

import java.util.Locale;

public class AdminReportsActivity extends AppCompatActivity {

    private LinearLayout reportContainer;
    private TextView tvReportStatus;
    private Button btnRefresh;

    private DatabaseReference database;

    private ValueEventListener usersListener;
    private ValueEventListener dealsListener;
    private ValueEventListener bookingsListener;

    private int totalUsers;
    private int customers;
    private int suppliers;
    private int admins;

    private int totalDeals;
    private int approvedDeals;
    private int rejectedDeals;
    private int availableDeals;

    private int totalBookings;
    private int confirmedBookings;
    private int pendingBookings;
    private int cancelledBookings;

    private double totalBookingValue;
    private double confirmedRevenue;

    private boolean usersLoaded;
    private boolean dealsLoaded;
    private boolean bookingsLoaded;

    private boolean usersFailed;
    private boolean dealsFailed;
    private boolean bookingsFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reports);

        reportContainer = findViewById(R.id.reportContainer);
        tvReportStatus = findViewById(R.id.tvReportStatus);
        btnRefresh = findViewById(R.id.btnRefreshReports);

        database = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        btnRefresh.setOnClickListener(v -> loadReports());

        loadReports();
    }

    private void loadReports() {
        removeListeners();

        usersLoaded = false;
        dealsLoaded = false;
        bookingsLoaded = false;

        usersFailed = false;
        dealsFailed = false;
        bookingsFailed = false;

        totalUsers = customers = suppliers = admins = 0;
        totalDeals = approvedDeals = rejectedDeals = availableDeals = 0;
        totalBookings = confirmedBookings = pendingBookings = cancelledBookings = 0;

        totalBookingValue = 0;
        confirmedRevenue = 0;

        reportContainer.removeAllViews();
        tvReportStatus.setText("Loading reports from Firebase...");

        usersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                totalUsers = 0;
                customers = suppliers = admins = 0;

                for (DataSnapshot user : snapshot.getChildren()) {
                    totalUsers++;

                    String role = getStringValue(user, "role", "Role", "userRole");

                    if (role == null) {
                        continue;
                    }

                    if (role.equalsIgnoreCase("Customer")) {
                        customers++;
                    } else if (role.equalsIgnoreCase("Supplier")) {
                        suppliers++;
                    } else if (role.equalsIgnoreCase("Admin")) {
                        admins++;
                    }
                }

                usersLoaded = true;
                renderReportsIfReady();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                usersFailed = true;
                usersLoaded = true;
                renderReportsIfReady();
                showError(error);
            }
        };

        dealsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                totalDeals = 0;
                approvedDeals = rejectedDeals = availableDeals = 0;

                for (DataSnapshot deal : snapshot.getChildren()) {
                    totalDeals++;

                    String status = getStringValue(deal, "status");

                    Boolean available = deal.child("available")
                            .getValue(Boolean.class);

                    if ("Approved".equalsIgnoreCase(status)) {
                        approvedDeals++;
                    }

                    if ("Rejected".equalsIgnoreCase(status)) {
                        rejectedDeals++;
                    }

                    if (Boolean.TRUE.equals(available)) {
                        availableDeals++;
                    }
                }

                dealsLoaded = true;
                renderReportsIfReady();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                dealsFailed = true;
                dealsLoaded = true;
                renderReportsIfReady();
                showError(error);
            }
        };

        bookingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                totalBookings = 0;
                confirmedBookings = pendingBookings = cancelledBookings = 0;

                totalBookingValue = 0;
                confirmedRevenue = 0;

                for (DataSnapshot booking : snapshot.getChildren()) {
                    totalBookings++;

                    String status = getStringValue(
                            booking, "status", "bookingStatus"
                    );

                    if (status != null) {
                        if (status.equalsIgnoreCase("Confirmed")
                                || status.equalsIgnoreCase("Completed")) {
                            confirmedBookings++;
                        } else if (status.equalsIgnoreCase("Pending")) {
                            pendingBookings++;
                        } else if (status.equalsIgnoreCase("Cancelled")
                                || status.equalsIgnoreCase("Canceled")) {
                            cancelledBookings++;
                        }
                    }

                    Double price = readPrice(booking);

                    if (price != null) {
                        totalBookingValue += price;

                        if (status != null
                                && (status.equalsIgnoreCase("Confirmed")
                                || status.equalsIgnoreCase("Completed"))) {
                            confirmedRevenue += price;
                        }
                    }
                }

                bookingsLoaded = true;
                renderReportsIfReady();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                bookingsFailed = true;
                bookingsLoaded = true;
                renderReportsIfReady();
                showError(error);
            }
        };

        database.child("users").addValueEventListener(usersListener);
        database.child("deals").addValueEventListener(dealsListener);
        database.child("bookings").addValueEventListener(bookingsListener);
    }

    private String getStringValue(DataSnapshot snapshot, String... fields) {
        for (String field : fields) {
            Object value = snapshot.child(field).getValue();

            if (value instanceof String) {
                String text = ((String) value).trim();

                if (!text.isEmpty()) {
                    return text;
                }
            }
        }

        return null;
    }

    private Double readPrice(DataSnapshot booking) {
        String[] fields = {
                "totalPrice",
                "totalAmount",
                "price",
                "amount"
        };

        for (String field : fields) {
            Object value = booking.child(field).getValue();

            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }

            if (value instanceof String) {
                try {
                    String cleaned = ((String) value)
                            .replace("$", "")
                            .replace(",", "")
                            .trim();

                    return Double.parseDouble(cleaned);
                } catch (NumberFormatException ignored) {
                    // Try the next supported price field.
                }
            }
        }

        return null;
    }

    private void renderReportsIfReady() {
        if (!usersLoaded || !dealsLoaded || !bookingsLoaded) {
            return;
        }

        reportContainer.removeAllViews();

        addSection("USER REPORT");

        if (usersFailed) {
            addMetric("User data", "Unavailable");
        } else {
            addMetric("Total users", totalUsers);
            addMetric("Customers", customers);
            addMetric("Suppliers", suppliers);
            addMetric("Admins", admins);
        }

        addSection("DEALS REPORT");

        if (dealsFailed) {
            addMetric("Deal data", "Unavailable");
        } else {
            addMetric("Total deals", totalDeals);
            addMetric("Approved deals", approvedDeals);
            addMetric("Rejected deals", rejectedDeals);
            addMetric("Available deals", availableDeals);
        }

        addSection("BOOKING REPORT");

        if (bookingsFailed) {
            addMetric("Booking data", "Unavailable");
        } else {
            addMetric("Total bookings", totalBookings);
            addMetric("Confirmed / completed", confirmedBookings);
            addMetric("Pending bookings", pendingBookings);
            addMetric("Cancelled bookings", cancelledBookings);

            addSection("REVENUE REPORT");

            addMetric(
                    "Total booking value",
                    String.format(Locale.US, "$%.2f", totalBookingValue)
            );

            addMetric(
                    "Confirmed revenue",
                    String.format(Locale.US, "$%.2f", confirmedRevenue)
            );
        }

        if (usersFailed || dealsFailed || bookingsFailed) {
            tvReportStatus.setText(
                    "Some reports could not load. Check your connection and refresh."
            );
        } else {
            tvReportStatus.setText("Reports updated from Firebase.");
        }
    }

    private void addSection(String title) {
        TextView heading = new TextView(this);

        heading.setText(title);
        heading.setTextSize(18);
        heading.setGravity(Gravity.START);
        heading.setPadding(0, 24, 0, 10);
        heading.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        reportContainer.addView(heading);
    }

    private void addMetric(String label, int value) {
        addMetric(label, String.valueOf(value));
    }

    private void addMetric(String label, String value) {
        TextView row = new TextView(this);

        row.setText(label + ":  " + value);
        row.setTextSize(15);
        row.setPadding(12, 10, 12, 10);

        reportContainer.addView(row);
    }

    private void showError(DatabaseError error) {
        Toast.makeText(
                this,
                "Firebase error: " + error.getMessage(),
                Toast.LENGTH_LONG
        ).show();
    }

    private void removeListeners() {
        if (database == null) {
            return;
        }

        if (usersListener != null) {
            database.child("users").removeEventListener(usersListener);
            usersListener = null;
        }

        if (dealsListener != null) {
            database.child("deals").removeEventListener(dealsListener);
            dealsListener = null;
        }

        if (bookingsListener != null) {
            database.child("bookings").removeEventListener(bookingsListener);
            bookingsListener = null;
        }
    }

    @Override
    protected void onDestroy() {
        removeListeners();
        super.onDestroy();
    }
}
