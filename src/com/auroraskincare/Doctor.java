package com.auroraskincare;

public class Doctor extends Person {
    private int id;
    private String specialization;

    public Doctor(int id, String name, String specialization, String phoneNumber, String email) {
        super(name, phoneNumber, email);
        this.id = id;
        this.specialization = specialization;
    }

    // Additional getters specific to Doctor
    public int getId() { return id; }
    public String getSpecialization() { return specialization; }
}