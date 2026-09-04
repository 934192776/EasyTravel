package com.example.easytravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easytravel.adapter.DealAdapter;
import com.example.easytravel.model.Deal;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFeatured;
    private DealAdapter dealAdapter;
    private List<Deal> allDeals = new ArrayList<>();

    private LinearLayout categoryCity, categoryAdventure, categoryFood, categoryNature;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ========== 找到控件 ==========
        recyclerViewFeatured = findViewById(R.id.recyclerViewFeatured);
        categoryCity = findViewById(R.id.categoryCity);
        categoryAdventure = findViewById(R.id.categoryAdventure);
        categoryFood = findViewById(R.id.categoryFood);
        categoryNature = findViewById(R.id.categoryNature);
        etSearch = findViewById(R.id.etSearch);

        // ========== 设置 RecyclerView ==========
        recyclerViewFeatured.setLayoutManager(new LinearLayoutManager(this));
        dealAdapter = new DealAdapter();
        recyclerViewFeatured.setAdapter(dealAdapter);

        // ========== 加载所有数据 ==========
        loadAllDeals();

        // ========== 搜索功能：按回车键搜索 ==========
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    performSearch(etSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        // ========== 分类点击 ==========
        categoryCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CitySearchActivity.class);
                startActivity(intent);
            }
        });

        categoryAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AdventureSearchActivity.class);
                startActivity(intent);
            }
        });

        categoryFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FoodSearchActivity.class);
                startActivity(intent);
            }
        });

        categoryNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NatureSearchActivity.class);
                startActivity(intent);
            }
        });

        // ========== 点击列表项跳转到详情 ==========
        dealAdapter.setOnItemClickListener(new DealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Deal deal) {
                Intent intent = new Intent(HomeActivity.this, DealDetailActivity.class);
                intent.putExtra("deal_data", deal);
                startActivity(intent);
            }
        });
    }

    // ========== 执行搜索 ==========
    private void performSearch(String query) {
        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
            return;
        }

        // 根据关键词跳转到对应分类页面
        String lowerQuery = query.toLowerCase();
        Intent intent = null;

        if (lowerQuery.contains("skydive") || lowerQuery.contains("sky")) {
            // 跳转到 Adventure 页面
            intent = new Intent(HomeActivity.this, AdventureSearchActivity.class);
            intent.putExtra("search_keyword", query);
        } else if (lowerQuery.contains("noodle") || lowerQuery.contains("food") || lowerQuery.contains("eat")) {
            // 跳转到 Food 页面
            intent = new Intent(HomeActivity.this, FoodSearchActivity.class);
            intent.putExtra("search_keyword", query);
        } else if (lowerQuery.contains("city") || lowerQuery.contains("tour") || lowerQuery.contains("walk")) {
            // 跳转到 City 页面
            intent = new Intent(HomeActivity.this, CitySearchActivity.class);
            intent.putExtra("search_keyword", query);
        } else if (lowerQuery.contains("nature") || lowerQuery.contains("wild") || lowerQuery.contains("hike")) {
            // 跳转到 Nature 页面
            intent = new Intent(HomeActivity.this, NatureSearchActivity.class);
            intent.putExtra("search_keyword", query);
        } else {
            // 默认跳转到搜索页面（或者显示提示）
            Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
            return;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    // ========== 加载所有数据 ==========
    private void loadAllDeals() {
        allDeals.add(new Deal("1", "Sky Tower & City Walking Tour", "Explore Auckland's iconic Sky Tower.", "City", "89.00", "", "Auckland City Tours", "Auckland CBD", true, "Tour"));
        allDeals.add(new Deal("2", "Rotorua Maori Cultural Experience", "Visit a Maori village and enjoy a traditional hangi feast.", "City", "249.00", "", "Cultural Tours NZ", "Rotorua", true, "Tour"));
        allDeals.add(new Deal("3", "Auckland Harbour Bridge Climb", "Climb the iconic Auckland Harbour Bridge.", "City", "159.00", "", "Adventure NZ", "Auckland Harbour", true, "Activity"));
        allDeals.add(new Deal("4", "Auckland War Memorial Museum", "Explore New Zealand's history at the Auckland Museum.", "City", "25.00", "", "Auckland Museum", "Auckland Domain", true, "Tour"));
        allDeals.add(new Deal("5", "Auckland Harbour Sailing", "2-hour sailing trip on Auckland Harbour.", "Adventure", "79.00", "", "Auckland Sailing Co", "Auckland Harbour", true, "Activity"));
        allDeals.add(new Deal("6", "Auckland Bridge Bungee Jump", "Jump off the iconic Auckland Harbour Bridge.", "Adventure", "199.00", "", "Adventure NZ", "Auckland Harbour", true, "Activity"));
        allDeals.add(new Deal("7", "Skydiving Over Auckland", "Experience the thrill of skydiving with ocean views.", "Adventure", "299.00", "", "Skydive Auckland", "Auckland", true, "Activity"));
        allDeals.add(new Deal("8", "Jet Boat Adventure", "High-speed jet boat ride on Auckland Harbour.", "Adventure", "129.00", "", "Harbour Jet", "Auckland Harbour", true, "Activity"));
        allDeals.add(new Deal("9", "Auckland Fish Market Tour", "Taste fresh seafood at the historic Auckland Fish Market.", "Food", "89.00", "", "Auckland Food Tours", "Auckland CBD", true, "Tour"));
        allDeals.add(new Deal("10", "Classic Kiwi Food Tour", "Try New Zealand's iconic lamb roast, meat pies, and pavlova.", "Food", "129.00", "", "Kiwi Food Adventures", "Auckland CBD", true, "Tour"));
        allDeals.add(new Deal("11", "Auckland Food Walk", "Explore Asian fusion street food in Auckland.", "Food", "99.00", "", "Auckland Food Walk", "Auckland CBD", true, "Tour"));
        allDeals.add(new Deal("12", "Noodle Making Class", "Learn to make authentic Asian noodles from scratch.", "Food", "75.00", "", "Asian Cooking School", "Auckland CBD", true, "Class"));
        allDeals.add(new Deal("13", "Waitomo Glowworm Caves Tour", "Experience the magical glowworm caves in Waitomo.", "Nature", "159.00", "", "Nature Adventures NZ", "Waitomo", true, "Tour"));
        allDeals.add(new Deal("14", "Rangitoto Island Volcano Hike", "Hike to the summit of Auckland's iconic volcano.", "Nature", "69.00", "", "Nature Adventures NZ", "Rangitoto Island", true, "Activity"));
        allDeals.add(new Deal("15", "Piha Beach & Rainforest Walk", "Explore the black sand beach and lush rainforest.", "Nature", "49.00", "", "Nature Tours NZ", "Piha", true, "Tour"));
        allDeals.add(new Deal("16", "Tiritiri Matangi Bird Sanctuary", "Bird watching tour on a protected island sanctuary.", "Nature", "85.00", "", "Bird Watching NZ", "Tiritiri Matangi", true, "Tour"));

        dealAdapter.setDeals(allDeals);
    }
}