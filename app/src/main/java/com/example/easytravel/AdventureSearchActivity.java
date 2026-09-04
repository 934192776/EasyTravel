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
import com.example.easytravel.adapter.DealAdapter;
import com.example.easytravel.model.Deal;
import java.util.ArrayList;
import java.util.List;

public class AdventureSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DealAdapter dealAdapter;
    private List<Deal> adventureDeals = new ArrayList<>();
    private List<Deal> filteredDeals = new ArrayList<>();
    private TextView btnBack;
    private EditText etSearchAdventure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_search);

        recyclerView = findViewById(R.id.recyclerViewAdventure);
        btnBack = findViewById(R.id.btnBack);
        etSearchAdventure = findViewById(R.id.etSearchAdventure);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dealAdapter = new DealAdapter();
        recyclerView.setAdapter(dealAdapter);

        loadAdventureDeals();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearchAdventure.addTextChangedListener(new TextWatcher() {
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
                Intent intent = new Intent(AdventureSearchActivity.this, DealDetailActivity.class);
                intent.putExtra("deal_data", deal);
                startActivity(intent);
            }
        });
    }

    private void filterDeals(String query) {
        filteredDeals.clear();
        if (query.isEmpty()) {
            filteredDeals.addAll(adventureDeals);
        } else {
            for (Deal deal : adventureDeals) {
                if (deal.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        deal.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredDeals.add(deal);
                }
            }
        }
        dealAdapter.setDeals(filteredDeals);
    }

    private void loadAdventureDeals() {
        adventureDeals.add(new Deal("1", "Auckland Harbour Sailing", "2-hour sailing trip on Auckland Harbour.", "Adventure", "79.00", "", "Auckland Sailing Co", "Auckland Harbour", true, "Activity"));
        adventureDeals.add(new Deal("2", "Auckland Bridge Bungee Jump", "Jump off the iconic Auckland Harbour Bridge.", "Adventure", "199.00", "", "Adventure NZ", "Auckland Harbour", true, "Activity"));
        adventureDeals.add(new Deal("3", "Skydiving Over Auckland", "Experience the thrill of skydiving with ocean views.", "Adventure", "299.00", "", "Skydive Auckland", "Auckland", true, "Activity"));
        adventureDeals.add(new Deal("4", "Jet Boat Adventure", "High-speed jet boat ride on Auckland Harbour.", "Adventure", "129.00", "", "Harbour Jet", "Auckland Harbour", true, "Activity"));

        filteredDeals.addAll(adventureDeals);
        dealAdapter.setDeals(filteredDeals);
    }
}