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
        btnBack = findViewById(R.id.btnBack); // 现在类型匹配了，不会闪退了
        etSearchFood = findViewById(R.id.etSearchFood);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dealAdapter = new DealAdapter();
        recyclerView.setAdapter(dealAdapter);

        loadFoodDeals();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearchFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterDeals(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        dealAdapter.setOnItemClickListener(new DealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Deal deal) {
                Intent intent = new Intent(FoodSearchActivity.this, DealDetailActivity.class);
                intent.putExtra("deal_data", deal);
                startActivity(intent);
            }
        });
    }

    private void filterDeals(String query) {
        filteredDeals.clear();
        if (query.isEmpty()) {
            filteredDeals.addAll(foodDeals);
        } else {
            for (Deal deal : foodDeals) {
                if (deal.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        deal.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredDeals.add(deal);
                }
            }
        }
        dealAdapter.setDeals(filteredDeals);
    }

    private void loadFoodDeals() {
        foodDeals.add(new Deal("1", "Auckland Fish Market Tour", "Taste fresh seafood.", "Food", "Seafood", "89.00", "", "S01", "Auckland Food Tours", "Auckland CBD", 4.8, true));
        foodDeals.add(new Deal("2", "Classic Kiwi Food Tour", "Try NZ's iconic lamb roast.", "Food", "Classic Kiwi", "129.00", "", "S02", "Kiwi Food Adventures", "Auckland CBD", 4.9, true));
        foodDeals.add(new Deal("3", "Auckland Food Walk", "Explore Asian fusion street food.", "Food", "Asian Fusion", "99.00", "", "S03", "Auckland Food Walk", "Auckland CBD", 4.6, true));
        foodDeals.add(new Deal("4", "Noodle Making Class", "Learn to make authentic noodles.", "Food", "Cooking", "75.00", "", "S04", "Asian Cooking School", "Auckland CBD", 4.7, true));

        filteredDeals.addAll(foodDeals);
        dealAdapter.setDeals(filteredDeals);
    }
}