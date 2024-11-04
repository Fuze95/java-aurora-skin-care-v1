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

    // Constructor to use day instead of date
    public Appointment(String day, String time, Patient patient, Doctor doctor, String treatmentType, double treatmentPrice) {
        this.appointmentId = ++counter;
        // Validate and set day
        if (isValidDay(day)) {
            this.day = day;
        } else {
            throw new IllegalArgumentException("Invalid day. Must be Monday, Wednesday, Friday, or Saturday.");
        }
        this.time = time;
        this.patient = patient;
        this.doctor = doctor;
        this.treatmentType = treatmentType;
        this.treatmentPrice = treatmentPrice;
        this.isCompleted = false;
    }

    // Validation method for days
    private boolean isValidDay(String day) {
        return day.equalsIgnoreCase("Monday") || 
               day.equalsIgnoreCase("Wednesday") || 
               day.equalsIgnoreCase("Friday") || 
               day.equalsIgnoreCase("Saturday");
    }
    
    // Setters
    public void setDay(String day) {
        if (isValidDay(day)) {
            this.day = day;
        } else {
            throw new IllegalArgumentException("Invalid day. Must be Monday, Wednesday, Friday, or Saturday.");
        }
    }
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