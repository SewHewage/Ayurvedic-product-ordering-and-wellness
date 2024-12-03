package com.example.ayushwellness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Authentication and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Link UI components to their XML IDs
        EditText emailField = findViewById(R.id.et_email);
        EditText passwordField = findViewById(R.id.et_password);
        Button loginButton = findViewById(R.id.btn_login);
        TextView registerLink = findViewById(R.id.tv_register);

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            // Validate inputs
            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please fill all fields");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Invalid email format");
                return;
            }

            // Authenticate user
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                fetchUserRole(user.getUid());
                            }
                        } else {
                            String error = task.getException() != null ? task.getException().getMessage() : "Login failed";
                            showToast(error);
                        }
                    });
        });

        // Handle register link click
        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, wellnessRegister.class); // Navigate to the registration page
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if the user is already logged in
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            fetchUserRole(user.getUid());
        }
    }

    // Fetch the user's role from Firestore and navigate accordingly
    private void fetchUserRole(String userId) {
        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            Log.d("Firestore", "Document data: " + document.getData());
                            String type = document.getString("type");
                            if (type != null) {
                                navigateToDashboard(type);
                            } else {
                                showToast("User role is missing. Please contact support.");
                            }
                        } else {
                            Log.e("Firestore", "User document not found for UID: " + userId);
                            showToast("Your profile is incomplete. Please register again or contact support.");
                        }
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Log.e("Firestore", "Error fetching document: " + error);
                        showToast("Error fetching user role: " + error);
                    }
                });
    }



    // Navigate to the appropriate dashboard based on the user's role
    private void navigateToDashboard(String type) {
        Intent intent;
        switch (type) {
            case "admin":
                intent = new Intent(this, AdminDashboardActivity.class);
                break;
            case "customer":
                intent = new Intent(this, CustomerDashboardActivity.class);
                break;
            case "consultant":
                intent = new Intent(this, ConsultantDashboardActivity.class);
                break;
            default:
                Log.e("Firestore", "Invalid role: " + type);
                showToast("Invalid user role. Please contact support.");
                return;
        }
        startActivity(intent);
        finish(); // Close the current activity
    }


    // Helper method to show toast messages
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
