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

public class NatureSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DealAdapter dealAdapter;
    private List<Deal> natureDeals = new ArrayList<>();
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature_search);

        recyclerView = findViewById(R.id.recyclerViewNature);
        btnBack = findViewById(R.id.btnBack);

        TextView tvTitle = findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText("🌿 Auckland Nature & Wildlife");
        }

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            dealAdapter = new DealAdapter();
            recyclerView.setAdapter(dealAdapter);
        }

        loadNatureDeals();

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

    private void loadNatureDeals() {
        natureDeals.add(new Deal("1", "Waitomo Glowworm Caves Tour", "Experience the magical glowworm caves in Waitomo.", "Nature", "159.00", "", "Nature Adventures NZ", "Waitomo", true, "Tour"));
        natureDeals.add(new Deal("2", "Rangitoto Island Volcano Hike", "Hike to the summit of Auckland's iconic volcano.", "Nature", "69.00", "", "Nature Adventures NZ", "Rangitoto Island", true, "Activity"));
        natureDeals.add(new Deal("3", "Piha Beach & Rainforest Walk", "Explore the black sand beach and lush rainforest.", "Nature", "49.00", "", "Nature Tours NZ", "Piha", true, "Tour"));
        natureDeals.add(new Deal("4", "Tiritiri Matangi Bird Sanctuary", "Bird watching tour on a protected island sanctuary.", "Nature", "85.00", "", "Bird Watching NZ", "Tiritiri Matangi", true, "Tour"));

        if (dealAdapter != null) {
            dealAdapter.setDeals(natureDeals);
        }
    }
}