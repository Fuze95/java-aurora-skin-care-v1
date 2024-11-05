package com.auroraskincare;

/*
* Represents a doctor in the clinic system.
* Extends the Person class to inherit basic personal information while adding
* doctor-specific attributes like ID and specialization.
*/

public class Doctor extends Person {
    private int id;
    private String specialization;

    //Constructor
    public Doctor(int id, String name, String specialization, String phoneNumber, String email) {
        super(name, phoneNumber, email);
        this.id = id;
        this.specialization = specialization;
    }

    // Additional getters specific to Doctor
    public int getId() { return id; }
    public String getSpecialization() { return specialization; }
}