package com.example.ayushwellness;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class ViewAppointmentsActivity extends AppCompatActivity {

    private ListView appointmentsListView;
    private ArrayList<String> appointmentsList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);

        appointmentsListView = findViewById(R.id.appointmentsListView);
        appointmentsList = new ArrayList<>();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch appointments from Firestore
        fetchAppointments();
    }

    private void fetchAppointments() {
        // Reference to the "appointments" collection
        CollectionReference appointmentsRef = db.collection("Appointments");

        // Fetch the data
        appointmentsRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot documents = task.getResult();
                        if (documents != null) {
                            for (QueryDocumentSnapshot document : documents) {
                                // Retrieve each appointment field
                                String appointment = document.getString("title") + " at " +
                                        document.getString("time");
                                appointmentsList.add(appointment);
                            }
                            // Set up the adapter to display the data
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                    android.R.layout.simple_list_item_1, appointmentsList);
                            appointmentsListView.setAdapter(adapter);
                        }
                    } else {
                        // Handle errors
                        Toast.makeText(ViewAppointmentsActivity.this, "Error getting documents.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
