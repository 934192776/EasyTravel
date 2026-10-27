package com.example.easytravel;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ManageUsersActivity extends AppCompatActivity {

    private Button btnBack;
    private Button btnRefreshUsers;
    private EditText etSearchUser;
    private LinearLayout userListContainer;
    private TextView tvUserCount;
    private TextView tvUserStatus;

    private final List<UserEntry> users = new ArrayList<>();
    private DatabaseReference usersRef;
    private ValueEventListener usersListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        btnBack = findViewById(R.id.btnBack);
        btnRefreshUsers = findViewById(R.id.btnRefreshUsers);
        etSearchUser = findViewById(R.id.etSearchUser);
        userListContainer = findViewById(R.id.userListContainer);
        tvUserCount = findViewById(R.id.tvUserCount);
        tvUserStatus = findViewById(R.id.tvUserStatus);

        usersRef = FirebaseDatabase.getInstance().getReference("users");

        btnBack.setOnClickListener(v -> finish());
        btnRefreshUsers.setOnClickListener(v -> loadUsers());

        etSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                displayUsers();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        loadUsers();
    }

    private void loadUsers() {
        tvUserStatus.setText("Loading users...");

        if (usersListener != null) {
            usersRef.removeEventListener(usersListener);
        }

        usersListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();

                for (DataSnapshot child : snapshot.getChildren()) {
                    String id = child.getKey();
                    String name = child.child("fullName").getValue(String.class);
                    String email = child.child("email").getValue(String.class);
                    String role = child.child("role").getValue(String.class);

                    if (name == null) {
                        name = child.child("name").getValue(String.class);
                    }

                    if (email == null) {
                        email = "";
                    }

                    if (role == null) {
                        role = "Unspecified";
                    }

                    users.add(new UserEntry(
                            id == null ? "" : id,
                            name == null || name.trim().isEmpty()
                                    ? "Unnamed user" : name,
                            email,
                            role
                    ));
                }

                displayUsers();
                tvUserStatus.setText(
                        users.isEmpty() ? "No users found in Firebase."
                                : "User data loaded."
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tvUserStatus.setText("Unable to load users.");
                Toast.makeText(
                        ManageUsersActivity.this,
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        };

        usersRef.addValueEventListener(usersListener);
    }

    private void displayUsers() {
        if (userListContainer == null) return;

        userListContainer.removeAllViews();

        String query = etSearchUser.getText()
                .toString().trim().toLowerCase(Locale.ROOT);

        int shown = 0;

        for (UserEntry user : users) {
            String searchable = (user.name + " " + user.email + " "
                    + user.role).toLowerCase(Locale.ROOT);

            if (!searchable.contains(query)) continue;

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(32, 24, 32, 24);

            LinearLayout.LayoutParams cardParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
            cardParams.setMargins(0, 0, 0, 20);
            card.setLayoutParams(cardParams);
            card.setBackgroundResource(
                    android.R.drawable.dialog_holo_light_frame
            );

            TextView nameView = new TextView(this);
            nameView.setText(user.name);
            nameView.setTextSize(18);
            nameView.setTextColor(0xFF172554);
            nameView.setTypeface(null, android.graphics.Typeface.BOLD);

            TextView emailView = new TextView(this);
            emailView.setText("Email: " +
                    (user.email.isEmpty() ? "Not provided" : user.email));
            emailView.setTextSize(14);

            TextView roleView = new TextView(this);
            roleView.setText("Role: " + user.role);
            roleView.setTextSize(14);

            card.addView(nameView);
            card.addView(emailView);
            card.addView(roleView);
            userListContainer.addView(card);

            shown++;
        }

        tvUserCount.setText("Users: " + shown);

        if (shown == 0) {
            TextView emptyView = new TextView(this);
            emptyView.setText(users.isEmpty()
                    ? "No users available."
                    : "No users match your search.");
            emptyView.setTextSize(16);
            emptyView.setPadding(8, 24, 8, 24);
            userListContainer.addView(emptyView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (usersRef != null && usersListener != null) {
            usersRef.removeEventListener(usersListener);
        }
    }

    private static class UserEntry {
        final String id;
        final String name;
        final String email;
        final String role;

        UserEntry(String id, String name, String email, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
        }
    }
}