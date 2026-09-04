package com.example.easytravel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddDealActivity extends AppCompatActivity {

    private EditText etTitle, etPrice, etCategory, etDescription;
    private Button btnSubmit, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);

        etTitle = findViewById(R.id.etTitle);
        etPrice = findViewById(R.id.etPrice);
        etCategory = findViewById(R.id.etCategory);
        etDescription = findViewById(R.id.etDescription);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
                String category = etCategory.getText().toString().trim();
                String description = etDescription.getText().toString().trim();

                if (title.isEmpty() || price.isEmpty() || category.isEmpty() || description.isEmpty()) {
                    Toast.makeText(AddDealActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(AddDealActivity.this, "✅ Deal Added Successfully!\n" + title + " - $" + price, Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}