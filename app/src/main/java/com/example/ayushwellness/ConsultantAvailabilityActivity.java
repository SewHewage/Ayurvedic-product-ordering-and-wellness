package com.example.ayushwellness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ConsultantAvailabilityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConsultantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_availability);

        recyclerView = findViewById(R.id.rv_consultants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list of consultants
        List<Consultant> consultants = new ArrayList<>();
        consultants.add(new Consultant("Dr. John Doe", "Cardiologist", "Available: 9:00 AM - 3:00 PM", R.drawable.doctor1));
        consultants.add(new Consultant("Dr. Jane Smith", "Dermatologist", "Available: 10:00 AM - 4:00 PM", R.drawable.doctor2));
        consultants.add(new Consultant("Dr. Alan Brown", "Orthopedic", "Available: 8:00 AM - 2:00 PM", R.drawable.doctor3));
        consultants.add(new Consultant("Dr. Emily White", "Pediatrician", "Available: 11:00 AM - 5:00 PM", R.drawable.doctor4));

        // Set up the adapter
        adapter = new ConsultantAdapter(consultants);
        recyclerView.setAdapter(adapter);
    }

    static class Consultant {
        String name, specialization, availability;
        int imageResId;

        public Consultant(String name, String specialization, String availability, int imageResId) {
            this.name = name;
            this.specialization = specialization;
            this.availability = availability;
            this.imageResId = imageResId;
        }
    }

    static class ConsultantAdapter extends RecyclerView.Adapter<ConsultantAdapter.ConsultantViewHolder> {
        private final List<Consultant> consultants;

        public ConsultantAdapter(List<Consultant> consultants) {
            this.consultants = consultants;
        }

        @NonNull
        @Override
        public ConsultantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultant, parent, false);
            return new ConsultantViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ConsultantViewHolder holder, int position) {
            Consultant consultant = consultants.get(position);
            holder.doctorName.setText(consultant.name);
            holder.specialization.setText(consultant.specialization);
            holder.availability.setText(consultant.availability);
            holder.doctorImage.setImageResource(consultant.imageResId);
        }

        @Override
        public int getItemCount() {
            return consultants.size();
        }

        static class ConsultantViewHolder extends RecyclerView.ViewHolder {
            TextView doctorName, specialization, availability;
            ImageView doctorImage;

            public ConsultantViewHolder(@NonNull View itemView) {
                super(itemView);
                doctorName = itemView.findViewById(R.id.tv_doctor_name);
                specialization = itemView.findViewById(R.id.tv_specialization);
                availability = itemView.findViewById(R.id.tv_availability);
                doctorImage = itemView.findViewById(R.id.iv_doctor_image);
            }
        }
    }
}
