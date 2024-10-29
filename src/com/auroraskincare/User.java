package com.auroraskincare;

public class User extends Person {
    private String username;
    private String password;
    private String role;

    public User(String name, String phoneNumber, String email, String username, String password, String role) {
        super(name, phoneNumber, email);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters specific to User
    public String getUsername() { return username; }
    public String getRole() { return role; }
    
    // Method to verify password
    public boolean verifyPassword(String inputPassword) {
        return password.equals(inputPassword);
    }
}