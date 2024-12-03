package com.example.ayushwellness.models;

public class Treatment {
    private String treatmentId;
    private String name;
    private String description;
    private String duration;
    private double cost;

    public Treatment() {
        // Default constructor required for Firebase
    }

    public Treatment(String treatmentId, String name, String description, String duration, double cost) {
        this.treatmentId = treatmentId;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.cost = cost;
    }

    // Getters and Setters
    public String getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(String treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
