package com.example.ayushwellness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class welness_dashboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welness_dashboard);

        // Initialize buttons
        Button customerButton = findViewById(R.id.customerButton);
        Button consultantButton = findViewById(R.id.consultantButton);
        Button adminButton = findViewById(R.id.adminButton);

        // Navigate to Customer Dashboard
        customerButton.setOnClickListener(v -> {
            Intent intent = new Intent(welness_dashboard.this, CustomerDashboardActivity.class);
            startActivity(intent);
        });

        // Navigate to Wellness Consultant Dashboard
        consultantButton.setOnClickListener(v -> {
            Intent intent = new Intent(welness_dashboard.this, ConsultantDashboardActivity.class);
            startActivity(intent);
        });

        // Navigate to Admin Dashboard
        adminButton.setOnClickListener(v -> {
            Intent intent = new Intent(welness_dashboard.this, AdminDashboardActivity.class);
            startActivity(intent);
        });
    }
}
