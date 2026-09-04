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

public class AdventureSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DealAdapter dealAdapter;
    private List<Deal> adventureDeals = new ArrayList<>();
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_search);

        recyclerView = findViewById(R.id.recyclerViewAdventure);
        btnBack = findViewById(R.id.btnBack);

        TextView tvTitle = findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText("🎢 Auckland Adventure Activities");
        }

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            dealAdapter = new DealAdapter();
            recyclerView.setAdapter(dealAdapter);
        }

        loadAdventureDeals();

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

    private void loadAdventureDeals() {
        adventureDeals.add(new Deal("1", "Auckland Harbour Sailing", "2-hour sailing trip on Auckland Harbour.", "Adventure", "79.00", "", "Auckland Sailing Co", "Auckland Harbour", true, "Activity"));
        adventureDeals.add(new Deal("2", "Auckland Bridge Bungee Jump", "Jump off the iconic Auckland Harbour Bridge.", "Adventure", "199.00", "", "Adventure NZ", "Auckland Harbour", true, "Activity"));
        adventureDeals.add(new Deal("3", "Skydiving Over Auckland", "Experience the thrill of skydiving with ocean views.", "Adventure", "299.00", "", "Skydive Auckland", "Auckland", true, "Activity"));
        adventureDeals.add(new Deal("4", "Jet Boat Adventure", "High-speed jet boat ride on Auckland Harbour.", "Adventure", "129.00", "", "Harbour Jet", "Auckland Harbour", true, "Activity"));

        if (dealAdapter != null) {
            dealAdapter.setDeals(adventureDeals);
        }
    }
}