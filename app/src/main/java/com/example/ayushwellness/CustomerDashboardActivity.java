package com.example.ayushwellness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CustomerDashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        auth = FirebaseAuth.getInstance();

        // View Ayurvedic Products
        findViewById(R.id.view_products_layout).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        });

        // Check Consultant Availability
        findViewById(R.id.check_consultant_layout).setOnClickListener(v -> {
            Intent intent = new Intent(this, ConsultantAvailabilityActivity.class);
            startActivity(intent);
        });

        // Make an Appointment
        findViewById(R.id.make_appointment_layout).setOnClickListener(v -> {
            Intent intent = new Intent(this, MakeAppointmentActivity.class);
            startActivity(intent);
        });

        // Buy Ayurvedic Products
        findViewById(R.id.buy_products_layout).setOnClickListener(v -> {
            Intent intent = new Intent(this, BuyProductsActivity.class);
            startActivity(intent);
        });

        // Track Orders
        findViewById(R.id.track_orders_layout).setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackOrderActivity.class);
            startActivity(intent);
        });

        // View Cart
        findViewById(R.id.view_cart_layout).setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewCartActivity.class);
            startActivity(intent);
        });

        // View Appointments
        findViewById(R.id.view_appointments_layout).setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewAppointmentsActivity.class);
            startActivity(intent);
        });

        // Logout
        findViewById(R.id.logout_layout).setOnClickListener(v -> {
            auth.signOut();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
