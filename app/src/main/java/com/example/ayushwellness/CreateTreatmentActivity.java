package com.example.ayushwellness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ayushwellness.models.Treatment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTreatmentActivity extends AppCompatActivity {

    private EditText treatmentName, treatmentDescription, treatmentDuration, treatmentCost;
    private Button saveTreatmentButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_treatment);

        treatmentName = findViewById(R.id.treatmentName);
        treatmentDescription = findViewById(R.id.treatmentDescription);
        treatmentDuration = findViewById(R.id.treatmentDuration);
        treatmentCost = findViewById(R.id.treatmentCost);
        saveTreatmentButton = findViewById(R.id.saveTreatmentButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("Treatments");

        saveTreatmentButton.setOnClickListener(view -> saveTreatment());
    }

    private void saveTreatment() {
        String name = treatmentName.getText().toString();
        String description = treatmentDescription.getText().toString();
        String duration = treatmentDuration.getText().toString();
        String costStr = treatmentCost.getText().toString();

        if (name.isEmpty() || description.isEmpty() || duration.isEmpty() || costStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double cost = Double.parseDouble(costStr);
        String treatmentId = databaseReference.push().getKey();

        Treatment treatment = new Treatment(treatmentId, name, description, duration, cost);

        if (treatmentId != null) {
            databaseReference.child(treatmentId).setValue(treatment).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateTreatmentActivity.this, "Treatment saved successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Return to the previous screen
                } else {
                    Toast.makeText(CreateTreatmentActivity.this, "Failed to save treatment", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
