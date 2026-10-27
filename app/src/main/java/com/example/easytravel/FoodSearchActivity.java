package com.example.easytravel;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FoodSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DealAdapter dealAdapter;
    private List<Deal> foodDeals = new ArrayList<>();
    private List<Deal> filteredDeals = new ArrayList<>();
    private TextView btnBack;
    private EditText etSearchFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        recyclerView = findViewById(R.id.recyclerViewFood);
        btnBack = findViewById(R.id.btnBack);
        etSearchFood = findViewById(R.id.etSearchFood);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dealAdapter = new DealAdapter();
        recyclerView.setAdapter(dealAdapter);

        loadFoodDeals();

        btnBack.setOnClickListener(v -> finish());

        etSearchFood.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { filterDeals(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        dealAdapter.setOnItemClickListener(deal -> {
            Intent intent = new Intent(FoodSearchActivity.this, DealDetailActivity.class);
            intent.putExtra("deal_data", deal);
            startActivity(intent);
        });
    }

    private void filterDeals(String query) {
        filteredDeals.clear();
        if (query.isEmpty()) {
            filteredDeals.addAll(foodDeals);
        } else {
            for (Deal deal : foodDeals) {
                if (deal.getTitle().toLowerCase().contains(query.toLowerCase())) filteredDeals.add(deal);
            }
        }
        dealAdapter.setDeals(filteredDeals);
    }

    private void loadFoodDeals() {
        // 👇 填入了稳定的网络图
        foodDeals.add(new Deal("1", "Auckland Fish Market Tour", "Taste fresh seafood.", "Food", "89.00", "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=500", "Auckland Food Tours", "Auckland CBD", true, "Tour"));
        foodDeals.add(new Deal("2", "Classic Kiwi Food Tour", "Try NZ's iconic lamb roast.", "Food", "129.00", "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?w=500", "Kiwi Food Adventures", "Auckland CBD", true, "Tour"));
        foodDeals.add(new Deal("3", "Auckland Food Walk", "Explore Asian fusion street food.", "Food", "99.00", "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=500", "Auckland Food Walk", "Auckland CBD", true, "Tour"));
        foodDeals.add(new Deal("4", "Noodle Making Class", "Learn to make authentic noodles.", "Food", "75.00", "https://images.unsplash.com/photo-1552611052-33e04de081de?w=500", "Asian Cooking School", "Auckland CBD", true, "Class"));

        filteredDeals.addAll(foodDeals);
        dealAdapter.setDeals(filteredDeals);
    }
}