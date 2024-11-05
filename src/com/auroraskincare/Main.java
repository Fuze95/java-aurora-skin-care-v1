package com.auroraskincare;

import java.util.Scanner;

/*
 * Main entry point class for the Aurora Skin Care Clinic System.
 * Provides a command-line interface for user interaction with the system.
 */

public class Main {
    public static void main(String[] args) {
        ClinicSystem system = new ClinicSystem();
        
        while (true) {
            try {
                // Display the welcome message and initial menu options
                System.out.println("\n=== Welcome to Aurora Skin Care Clinic ===");
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.print("Enter choice: ");
                
                // Create Scanner object to read user input
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        if (system.login()) {
                            System.out.println("Login successful!");
                            system.showMainMenu();
                        } else {
                            System.out.println("Login failed. Please try again.");
                        }
                        break;
                    case 2:
                        // Exit the application
                        System.out.println("Thank you for using Aurora Skin Care Clinic System!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice! Please enter 1 or 2.");
                }
            } catch (Exception e) {
                // Handle any unexpected errors that might occur during execution
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }
}