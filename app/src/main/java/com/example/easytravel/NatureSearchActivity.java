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

public class NatureSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DealAdapter dealAdapter;
    private List<Deal> natureDeals = new ArrayList<>();
    private List<Deal> filteredDeals = new ArrayList<>();
    private TextView btnBack;
    private EditText etSearchNature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature_search);

        recyclerView = findViewById(R.id.recyclerViewNature);
        btnBack = findViewById(R.id.btnBack);
        etSearchNature = findViewById(R.id.etSearchNature);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dealAdapter = new DealAdapter();
        recyclerView.setAdapter(dealAdapter);

        loadNatureDeals();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearchNature.addTextChangedListener(new TextWatcher() {
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
                Intent intent = new Intent(NatureSearchActivity.this, DealDetailActivity.class);
                intent.putExtra("deal_data", deal);
                startActivity(intent);
            }
        });
    }

    private void filterDeals(String query) {
        filteredDeals.clear();
        if (query.isEmpty()) {
            filteredDeals.addAll(natureDeals);
        } else {
            for (Deal deal : natureDeals) {
                if (deal.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        deal.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredDeals.add(deal);
                }
            }
        }
        dealAdapter.setDeals(filteredDeals);
    }

    private void loadNatureDeals() {
        natureDeals.add(new Deal("1", "Waitomo Glowworm Caves Tour", "Experience the magical glowworm caves in Waitomo.", "Nature", "159.00", "", "Nature Adventures NZ", "Waitomo", true, "Tour"));
        natureDeals.add(new Deal("2", "Rangitoto Island Volcano Hike", "Hike to the summit of Auckland's iconic volcano.", "Nature", "69.00", "", "Nature Adventures NZ", "Rangitoto Island", true, "Activity"));
        natureDeals.add(new Deal("3", "Piha Beach & Rainforest Walk", "Explore the black sand beach and lush rainforest.", "Nature", "49.00", "", "Nature Tours NZ", "Piha", true, "Tour"));
        natureDeals.add(new Deal("4", "Tiritiri Matangi Bird Sanctuary", "Bird watching tour on a protected island sanctuary.", "Nature", "85.00", "", "Bird Watching NZ", "Tiritiri Matangi", true, "Tour"));

        filteredDeals.addAll(natureDeals);
        dealAdapter.setDeals(filteredDeals);
    }
}