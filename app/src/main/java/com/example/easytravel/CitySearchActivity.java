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
import java.util.ArrayList;
import java.util.List;

public class CitySearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DealAdapter dealAdapter;
    private List<Deal> cityDeals = new ArrayList<>();
    private List<Deal> filteredDeals = new ArrayList<>();
    private TextView btnBack;
    private EditText etSearchCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);

        recyclerView = findViewById(R.id.recyclerViewCity);
        btnBack = findViewById(R.id.btnBack);
        etSearchCity = findViewById(R.id.etSearchCity);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dealAdapter = new DealAdapter();
        recyclerView.setAdapter(dealAdapter);

        loadCityDeals();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearchCity.addTextChangedListener(new TextWatcher() {
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
                Intent intent = new Intent(CitySearchActivity.this, DealDetailActivity.class);
                intent.putExtra("deal_data", deal);
                startActivity(intent);
            }
        });
    }

    private void filterDeals(String query) {
        filteredDeals.clear();
        if (query.isEmpty()) {
            filteredDeals.addAll(cityDeals);
        } else {
            for (Deal deal : cityDeals) {
                if (deal.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        deal.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredDeals.add(deal);
                }
            }
        }
        dealAdapter.setDeals(filteredDeals);
    }

    private void loadCityDeals() {
        cityDeals.add(new Deal("1", "Sky Tower & City Walking Tour", "Explore Auckland's iconic Sky Tower.", "City", "89.00", "", "Auckland City Tours", "Auckland CBD", true, "Tour"));
        cityDeals.add(new Deal("2", "Rotorua Maori Cultural Experience", "Visit a Maori village and enjoy a traditional hangi feast.", "City", "249.00", "", "Cultural Tours NZ", "Rotorua", true, "Tour"));
        cityDeals.add(new Deal("3", "Auckland Harbour Bridge Climb", "Climb the iconic Auckland Harbour Bridge.", "City", "159.00", "", "Adventure NZ", "Auckland Harbour", true, "Activity"));
        cityDeals.add(new Deal("4", "Auckland War Memorial Museum", "Explore New Zealand's history at the Auckland Museum.", "City", "25.00", "", "Auckland Museum", "Auckland Domain", true, "Tour"));

        filteredDeals.addAll(cityDeals);
        dealAdapter.setDeals(filteredDeals);
    }
}