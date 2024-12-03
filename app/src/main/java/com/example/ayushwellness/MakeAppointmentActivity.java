package com.example.ayushwellness;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ayushwellness.models.Appointment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MakeAppointmentActivity extends AppCompatActivity {

    private Spinner spinnerDoctors;
    private TextView tvSelectedDate, tvSelectedTime;
    private Button btnSelectDate, btnSelectTime, btnConfirmAppointment;

    private String selectedDoctor = "";
    private String selectedDate = "";
    private String selectedTime = "";

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        // Initialize views
        spinnerDoctors = findViewById(R.id.spinner_doctors);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        tvSelectedTime = findViewById(R.id.tv_selected_time);
        btnSelectDate = findViewById(R.id.btn_select_date);
        btnSelectTime = findViewById(R.id.btn_select_time);
        btnConfirmAppointment = findViewById(R.id.btn_confirm_appointment);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Populate doctors in spinner
        List<String> doctors = new ArrayList<>();
        doctors.add("Dr. John Doe - Cardiologist");
        doctors.add("Dr. Jane Smith - Dermatologist");
        doctors.add("Dr. Alan Brown - Orthopedic");
        doctors.add("Dr. Emily White - Pediatrician");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctors.setAdapter(adapter);

        // Date Picker
        btnSelectDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (datePicker, year, month, day) -> {
                selectedDate = day + "/" + (month + 1) + "/" + year;
                tvSelectedDate.setText("Selected Date: " + selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Time Picker
        btnSelectTime.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(this, (timePicker, hour, minute) -> {
                selectedTime = hour + ":" + (minute < 10 ? "0" + minute : minute);
                tvSelectedTime.setText("Selected Time: " + selectedTime);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        });

        // Confirm Appointment
        btnConfirmAppointment.setOnClickListener(view -> {
            selectedDoctor = spinnerDoctors.getSelectedItem().toString();

            if (selectedDoctor.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            saveAppointment(selectedDoctor, selectedDate, selectedTime);
        });
    }

    private void saveAppointment(String doctor, String date, String time) {
        // Generate a unique appointment ID
        String appointmentId = firestore.collection("Appointments").document().getId();

        // Create an Appointment object
        Appointment appointment = new Appointment(appointmentId, "User Name", date, time, doctor);

        // Save to Firestore
        firestore.collection("Appointments").document(appointmentId)
                .set(appointment)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Appointment Created Successfully!", Toast.LENGTH_LONG).show();
                    finish(); // Close the activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to Create Appointment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
