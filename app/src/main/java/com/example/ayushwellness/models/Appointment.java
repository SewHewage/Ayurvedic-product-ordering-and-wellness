package com.example.ayushwellness.models;

    public class Appointment {
        private String appointmentId;
        private String userName;
        private String appointmentDate;
        private String appointmentTime;
        private String doctorName;

        // No-arg constructor required for Firestore
        public Appointment() {
        }

        public Appointment(String appointmentId, String userName, String appointmentDate, String appointmentTime, String doctorName) {
            this.appointmentId = appointmentId;
            this.userName = userName;
            this.appointmentDate = appointmentDate;
            this.appointmentTime = appointmentTime;
            this.doctorName = doctorName;
        }

        // Getters and Setters
        public String getAppointmentId() {
            return appointmentId;
        }

        public void setAppointmentId(String appointmentId) {
            this.appointmentId = appointmentId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }
    }


