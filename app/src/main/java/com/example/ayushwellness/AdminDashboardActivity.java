package com.example.ayushwellness;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardActivity extends AppCompatActivity {

    // Declare the LinearLayouts (or sections)
    private LinearLayout manageProductsLayout;
    private LinearLayout viewAppointmentsLayout;
    private LinearLayout viewOrdersLayout;
    private LinearLayout manageOrdersLayout;
    private LinearLayout logoutLayout;

    private FirebaseAuth auth; // Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // Initialize views
        manageProductsLayout = findViewById(R.id.manage_products_layout);
        viewAppointmentsLayout = findViewById(R.id.view_appointments_layout);
        viewOrdersLayout = findViewById(R.id.view_orders_layout);
        manageOrdersLayout = findViewById(R.id.manage_orders_layout);
        logoutLayout = findViewById(R.id.logout_layout);

        // Set click listeners for each section
        manageProductsLayout = findViewById(R.id.manage_products_layout);
        if (manageProductsLayout == null) {
            Toast.makeText(this, "manageProductsLayout is null", Toast.LENGTH_LONG).show();
        }

        viewAppointmentsLayout = findViewById(R.id.view_customers_layout);
        if (viewAppointmentsLayout == null) {
            Toast.makeText(this, "viewAppointmentsLayout is null", Toast.LENGTH_LONG).show();
        }
        if (viewOrdersLayout != null) {
            viewOrdersLayout.setOnClickListener(v -> navigateToViewOrders());
        } else {
            Toast.makeText(this, "viewOrdersLayout is null", Toast.LENGTH_SHORT).show();
        }

        if (manageOrdersLayout != null) {
            manageOrdersLayout.setOnClickListener(v -> navigateToManageOrders());
        } else {
            Toast.makeText(this, "manageOrdersLayout is null", Toast.LENGTH_SHORT).show();
        }

        if (logoutLayout != null) {
            logoutLayout.setOnClickListener(v -> logout());
        } else {
            Toast.makeText(this, "logoutLayout is null", Toast.LENGTH_SHORT).show();
        }
    }

    // Navigate to Manage Products Activity
    private void navigateToManageProducts() {
        Intent intent = new Intent(AdminDashboardActivity.this, ManageProductActivity.class);
        startActivity(intent);
    }

    // Navigate to View Appointments Activity
    private void navigateToViewAppointments() {
        Intent intent = new Intent(AdminDashboardActivity.this, ViewAppointmentsActivity.class);
        startActivity(intent);
    }

    // Navigate to View Orders Activity
    private void navigateToViewOrders() {
        Intent intent = new Intent(AdminDashboardActivity.this, ViewOrdersActivity.class);
        startActivity(intent);
    }

    // Navigate to Manage Orders Activity
    private void navigateToManageOrders() {
        Intent intent = new Intent(AdminDashboardActivity.this, ManageOrdersActivity.class);
        startActivity(intent);
    }

    // Logout function
    private void logout() {
        auth.signOut(); // Sign out from Firebase
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AdminDashboardActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }
}
