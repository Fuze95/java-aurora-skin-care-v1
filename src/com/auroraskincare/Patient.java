package com.auroraskincare;

public class Patient extends Person {
    private String nic;

    public Patient(String nic, String name, String email, String phoneNumber) {
        super(name, phoneNumber, email);
        this.nic = nic;
    }

    // Additional getter specific to Patient
    public String getNic() { return nic; }
}