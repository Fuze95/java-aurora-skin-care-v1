package com.auroraskincare;

/*
* Represents a system user in the clinic management system.
* Extends the Person class to inherit basic personal information while adding
* user authentication and authorization attributes.
*/

public class User extends Person {
    private String username;
    private String password;
    private String role;

    /*
    * Constructor to create a new User instance.
    * Initializes both the inherited person attributes and user-specific authentication details.
    */
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