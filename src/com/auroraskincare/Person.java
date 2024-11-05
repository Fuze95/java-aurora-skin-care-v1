package com.auroraskincare;

/*
* Abstract class representing a person in the clinic system.
* Provides common attributes and behaviors for all person types
* (patients, doctors and users) in the system.
*/

public abstract class Person {
    protected String name;
    protected String phoneNumber;
    protected String email;

    //Constructor to initialize a person with basic information.
    public Person(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}