package com.example.easytravel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easytravel.adapter.DealAdapter;
import com.example.easytravel.model.Deal;
import java.util.ArrayList;
import java.util.List;

public class CitySearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DealAdapter dealAdapter;
    private List<Deal> cityDeals = new ArrayList<>();
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);

        recyclerView = findViewById(R.id.recyclerViewCity);
        btnBack = findViewById(R.id.btnBack);

        TextView tvTitle = findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText("🏙️ Auckland City Tours");
        }

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            dealAdapter = new DealAdapter();
            recyclerView.setAdapter(dealAdapter);
        }

        loadCityDeals();

        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        if (dealAdapter != null) {
            dealAdapter.setOnItemClickListener(new DealAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Deal deal) {
                    // 跳转到详情页
                }
            });
        }
    }

    private void loadCityDeals() {
        cityDeals.add(new Deal("1", "Sky Tower & City Walking Tour", "Explore Auckland's iconic Sky Tower.", "City", "89.00", "", "Auckland City Tours", "Auckland CBD", true, "Tour"));
        cityDeals.add(new Deal("2", "Rotorua Maori Cultural Experience", "Visit a Maori village and enjoy a traditional hangi feast.", "City", "249.00", "", "Cultural Tours NZ", "Rotorua", true, "Tour"));
        cityDeals.add(new Deal("3", "Auckland Harbour Bridge Climb", "Climb the iconic Auckland Harbour Bridge.", "City", "159.00", "", "Adventure NZ", "Auckland Harbour", true, "Activity"));
        cityDeals.add(new Deal("4", "Auckland War Memorial Museum", "Explore New Zealand's history at the Auckland Museum.", "City", "25.00", "", "Auckland Museum", "Auckland Domain", true, "Tour"));

        if (dealAdapter != null) {
            dealAdapter.setDeals(cityDeals);
        }
    }
}