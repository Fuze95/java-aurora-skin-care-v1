package com.auroraskincare;

/*
* Represents a patient in the clinic system.
* Extends the Person class to inherit basic personal information while adding
* patient-specific attributes like National Identity Card (NIC) number.
*/

public class Patient extends Person {
    private String nic;

    // Constructor
    public Patient(String nic, String name, String email, String phoneNumber) {
        super(name, phoneNumber, email);
        this.nic = nic;
    }

    // Additional getter specific to Patient
    public String getNic() { return nic; }
}