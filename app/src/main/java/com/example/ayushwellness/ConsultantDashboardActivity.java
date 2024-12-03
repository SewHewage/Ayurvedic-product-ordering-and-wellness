package com.example.ayushwellness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ConsultantDashboardActivity extends AppCompatActivity {

    // Declare the LinearLayouts (or sections)
    private LinearLayout createTreatmentLayout;
    private LinearLayout viewAppointmentsLayout;
    private LinearLayout logoutLayout;

    // Declare FirebaseAuth instance
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_dashboard);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Initialize views
        createTreatmentLayout = findViewById(R.id.create_treatment_layout);
        viewAppointmentsLayout = findViewById(R.id.view_appointments_layout);
        logoutLayout = findViewById(R.id.logout_layout);

        // Set click listeners for each section
        createTreatmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCreateTreatment();
            }
        });

        viewAppointmentsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToViewAppointments();
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    // Navigate to Create Treatment Activity
    private void navigateToCreateTreatment() {
        Intent intent = new Intent(ConsultantDashboardActivity.this, CreateTreatmentActivity.class);
        startActivity(intent);
    }

    // Navigate to View Appointments Activity
    private void navigateToViewAppointments() {
        Intent intent = new Intent(ConsultantDashboardActivity.this, ViewAppointmentsActivity.class);
        startActivity(intent);
    }

    // Logout function (finish the activity and go back to login screen)
    private void logout() {
        auth.signOut(); // Sign out from Firebase
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ConsultantDashboardActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }
}
