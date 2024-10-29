package com.auroraskincare;

public class Appointment {
    private static int counter = 1000;
    private int appointmentId;
    private String date;
    private String time;
    private Patient patient;
    private Doctor doctor;
    private String treatmentType;
    private double treatmentPrice;
    private boolean isCompleted;

    public Appointment(String date, String time, Patient patient, Doctor doctor, String treatmentType, double treatmentPrice) {
        this.appointmentId = ++counter;
        this.date = date;
        this.time = time;
        this.patient = patient;
        this.doctor = doctor;
        this.treatmentType = treatmentType;
        this.treatmentPrice = treatmentPrice;
        this.isCompleted = false;
    }

    // Add setters for updatable fields
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public void setTreatmentType(String treatmentType) { this.treatmentType = treatmentType; }
    public void setTreatmentPrice(double treatmentPrice) { this.treatmentPrice = treatmentPrice; }

    // Existing getters
    public int getAppointmentId() { return appointmentId; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public String getTreatmentType() { return treatmentType; }
    public double getTreatmentPrice() { return treatmentPrice; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}