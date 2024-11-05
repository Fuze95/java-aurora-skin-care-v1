package com.auroraskincare;

public class Appointment {
    private static int counter = 1000;
    private int appointmentId;
    private String day;
    private String time;
    private Patient patient;
    private Doctor doctor;
    private String treatmentType;
    private double treatmentPrice;
    private boolean isCompleted;

    // Constructor
    public Appointment(String day, String time, Patient patient, Doctor doctor, String treatmentType, double treatmentPrice) {
        this.appointmentId = ++counter;
        this.day = day;
        this.time = time;
        this.patient = patient;
        this.doctor = doctor;
        this.treatmentType = treatmentType;
        this.treatmentPrice = treatmentPrice;
        this.isCompleted = false;
    }
    
    // Setters
    public void setDay(String day) { this.day = day; }
    public void setTime(String time) { this.time = time; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public void setTreatmentType(String treatmentType) { this.treatmentType = treatmentType; }
    public void setTreatmentPrice(double treatmentPrice) { this.treatmentPrice = treatmentPrice; }

    // Getters
    public int getAppointmentId() { return appointmentId; }
    public String getDay() { return day; }
    public String getTime() { return time; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public String getTreatmentType() { return treatmentType; }
    public double getTreatmentPrice() { return treatmentPrice; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}