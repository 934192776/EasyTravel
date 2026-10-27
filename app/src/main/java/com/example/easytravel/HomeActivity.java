package com.example.easytravel;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFeatured;
    private DealAdapter dealAdapter;
    private List<Deal> allDeals = new ArrayList<>();
    private List<Deal> filteredDeals = new ArrayList<>();

    private LinearLayout categoryCity, categoryAdventure, categoryFood, categoryNature;
    private EditText etSearch;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 找到控件
        recyclerViewFeatured = findViewById(R.id.recyclerViewFeatured);
        categoryCity = findViewById(R.id.categoryCity);
        categoryAdventure = findViewById(R.id.categoryAdventure);
        categoryFood = findViewById(R.id.categoryFood);
        categoryNature = findViewById(R.id.categoryNature);
        etSearch = findViewById(R.id.etSearch);
        btnLogin = findViewById(R.id.btnLogin);

        // 设置 RecyclerView
        recyclerViewFeatured.setLayoutManager(new LinearLayoutManager(this));
        dealAdapter = new DealAdapter();
        recyclerViewFeatured.setAdapter(dealAdapter);

        // ========== 🔥 关键修改：从 Firebase 读取真实数据 ==========
        loadDealsFromFirebase();

        // ========== 右上角按钮逻辑 ==========
        btnLogin.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                Intent intent = new Intent(HomeActivity.this, CustomerDashboardActivity.class);
                intent.putExtra("user_email", currentUser.getEmail());
                startActivity(intent);
            } else {
                Toast.makeText(HomeActivity.this, "Please login to view your bookings.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        // ========== 搜索框实时过滤功能 ==========
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterDeals(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // 分类点击
        categoryCity.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, CitySearchActivity.class)));
        categoryAdventure.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AdventureSearchActivity.class)));
        categoryFood.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, FoodSearchActivity.class)));
        categoryNature.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NatureSearchActivity.class)));

        // 点击列表项跳转到详情
        dealAdapter.setOnItemClickListener(deal -> {
            Intent intent = new Intent(HomeActivity.this, DealDetailActivity.class);
            intent.putExtra("deal_data", deal);
            startActivity(intent);
        });
    }

    // ========== 从 Firebase 读取数据 ==========
    private void loadDealsFromFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("deals");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allDeals.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // 自动将 Firebase 的数据转换成 Deal 对象（包括价格、评分、图片）
                    Deal deal = dataSnapshot.getValue(Deal.class);
                    if (deal != null) {
                        allDeals.add(deal);
                    }
                }

                // 默认显示全部
                filteredDeals.clear();
                filteredDeals.addAll(allDeals);
                dealAdapter.setDeals(filteredDeals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 实时过滤本地数据
    private void filterDeals(String query) {
        filteredDeals.clear();
        if (query.isEmpty()) {
            filteredDeals.addAll(allDeals);
        } else {
            for (Deal deal : allDeals) {
                if (deal.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        deal.getCategory().toLowerCase().contains(query.toLowerCase())) {
                    filteredDeals.add(deal);
                }
            }
        }
        dealAdapter.setDeals(filteredDeals);
    }
}