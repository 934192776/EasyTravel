package com.example.easytravel;

import android.content.Intent;
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

public class FoodSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DealAdapter dealAdapter;
    private List<Deal> foodDeals = new ArrayList<>();
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        // ========== 找到控件 ==========
        recyclerView = findViewById(R.id.recyclerViewFood);
        btnBack = findViewById(R.id.btnBack);

        // ========== 设置标题 ==========
        TextView tvTitle = findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText("🍔 Auckland Food Tours");
        }

        // ========== 设置 RecyclerView ==========
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            dealAdapter = new DealAdapter();
            recyclerView.setAdapter(dealAdapter);
        }

        // ========== 加载 Food 数据（4个） ==========
        loadFoodDeals();

        // ========== 返回按钮 ==========
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        // ========== 点击跳转到详情 ==========
        if (dealAdapter != null) {
            dealAdapter.setOnItemClickListener(new DealAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Deal deal) {
                    Intent intent = new Intent(FoodSearchActivity.this, DealDetailActivity.class);
                    intent.putExtra("deal_data", deal);
                    startActivity(intent);
                }
            });
        }
    }

    private void loadFoodDeals() {
        foodDeals.add(new Deal(
                "1",
                "Auckland Fish Market Tour",
                "Taste fresh seafood at the historic Auckland Fish Market.",
                "Food",
                "89.00",
                "",
                "Auckland Food Tours",
                "Auckland CBD",
                true,
                "Tour"
        ));

        foodDeals.add(new Deal(
                "2",
                "Classic Kiwi Food Tour",
                "Try New Zealand's iconic lamb roast, meat pies, and pavlova.",
                "Food",
                "129.00",
                "",
                "Kiwi Food Adventures",
                "Auckland CBD",
                true,
                "Tour"
        ));

        foodDeals.add(new Deal(
                "3",
                "Auckland Food Walk",
                "Explore Asian fusion street food in Auckland.",
                "Food",
                "99.00",
                "",
                "Auckland Food Walk",
                "Auckland CBD",
                true,
                "Tour"
        ));

        foodDeals.add(new Deal(
                "4",
                "Noodle Making Class",
                "Learn to make authentic Asian noodles from scratch.",
                "Food",
                "75.00",
                "",
                "Asian Cooking School",
                "Auckland CBD",
                true,
                "Class"
        ));

        if (dealAdapter != null) {
            dealAdapter.setDeals(foodDeals);
        }
    }
}