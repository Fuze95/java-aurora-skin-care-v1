package com.auroraskincare;

/*
* Represents an appointment in the clinic system.
* Manages all appointment-related information including scheduling,
* patient-doctor assignments, treatment details, and completion status.
*/

public class Appointment {
    // Counter for generating unique appointment IDs, starting from 1000
    private static int counter = 1000;
    private int appointmentId;
    private String day;
    private String time;
    private Patient patient;
    private Doctor doctor;
    private String treatmentType;
    private double treatmentPrice;
    /* 
    * Flag indicating if the appointment has been completed
    * Appointment status shows 'pending' until an Invoice is issued 
    */
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