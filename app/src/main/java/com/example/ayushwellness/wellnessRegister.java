package com.example.ayushwellness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class wellnessRegister extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellness_register);

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Bind UI elements
        EditText emailField = findViewById(R.id.registerEmailField);
        EditText passwordField = findViewById(R.id.registerPasswordField);
        RadioGroup userTypeGroup = findViewById(R.id.userTypeGroup);
        Button registerButton = findViewById(R.id.registerButton);
        TextView backToLoginText = findViewById(R.id.backToLoginText);

        // Handle registration button click
        registerButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            int selectedId = userTypeGroup.getCheckedRadioButtonId();

            // Validate input
            if (email.isEmpty() || password.isEmpty() || selectedId == -1) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get selected user type
            RadioButton selectedOption = findViewById(selectedId);
            String userType = selectedOption.getText().toString().toLowerCase();

            // Register the user
            registerUser(email, password, userType);
        });

        // Handle "Back to Login" click
        backToLoginText.setOnClickListener(v -> {
            Intent intent = new Intent(wellnessRegister.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Closes the current activity
        });
    }

    /**
     * Method to register a new user.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @param type     The user's role/type (e.g., admin, customer).
     */
    private void registerUser(String email, String password, String type) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = auth.getCurrentUser().getUid();
                        // Prepare user data
                        Map<String, Object> user = new HashMap<>();
                        user.put("email", email);
                        user.put("type", type);

                        // Save user data to Firestore
                        db.collection("users").document(userId).set(user)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    finish(); // Close the current activity after successful registration
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error saving user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // Handle registration failure
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(this, "Registration failed: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
