package com.auroraskincare;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
* Handles the generation and formatting of invoices for appointments.
* Calculates fees, taxes, and creates a formatted invoice display.
*/

public class InvoiceGenerator {
    private static final int INVOICE_WIDTH = 50; // Width of the invoice in characters
    private static final String BORDER_CHAR = "#";
    private static final String SEPARATOR_CHAR = "-";
    private static final double REGISTRATION_FEE = 500.00; //Standard registration fee

public static double getRegistrationFee() {
    return REGISTRATION_FEE;
}
    private static final double TAX_RATE = 0.025; // 2.5% Tax

    private final Appointment appointment;

    // Constructor that takes appointment
    public InvoiceGenerator(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        this.appointment = appointment;
    }

    // Helper method to print centered text
    private void printCenteredLine(String text) {
        int padding = (INVOICE_WIDTH - text.length()) / 2;
        System.out.printf("%" + padding + "s%s%" + padding + "s%n", "", text, "");
    }

    private void printBorder() {
        System.out.println(BORDER_CHAR.repeat(INVOICE_WIDTH));
    }

    private void printSeparator() {
        System.out.println(SEPARATOR_CHAR.repeat(INVOICE_WIDTH));
    }

    private void printBillItem(String label, double amount) {
        System.out.printf("%-30s LKR %10.2f%n", label + ":", amount);
    }

    private void printPatientInfo(String label, String value) {
        System.out.printf("%-6s %-40s%n", label + ":", value);
    }

    // Main method to generate invoice
    public void generateInvoice() {
        if (appointment.isCompleted()) {
            throw new IllegalStateException("Invoice has already been generated for this appointment");
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Calculate total, rounded up to the nearest decimal number
        double treatmentPrice = appointment.getTreatmentPrice();
        double tax = Math.ceil((treatmentPrice + REGISTRATION_FEE) * TAX_RATE * 100) / 100;
        double total = treatmentPrice + REGISTRATION_FEE + tax;

        // Print invoice header
        System.out.println();
        printBorder();
        printCenteredLine("AURORA SKIN CARE - COLOMBO");
        printCenteredLine("INVOICE");
        printBorder();

        // Print datetime
        System.out.println("Generated Date & Time: " + now.format(dateTimeFormatter));
        
        // Print appointment details
        System.out.println("\nAPPOINTMENT DETAILS:");
        printSeparator();
        System.out.printf("Date: %-12s     Time: %s%n", 
                appointment.getDay(), appointment.getTime());
        System.out.printf("Appointment ID: %d%n", appointment.getAppointmentId());
        
        // Print patient details
        System.out.println("\nPATIENT DETAILS:");
        printSeparator();
        printPatientInfo("Name", appointment.getPatient().getName());
        printPatientInfo("NIC", appointment.getPatient().getNic());
        printPatientInfo("Phone", appointment.getPatient().getPhoneNumber());
        
        // Print treatment details
        System.out.println("\nTREATMENT INFORMATION:");
        printSeparator();
        System.out.printf("Doctor:    %-37s%n", appointment.getDoctor().getName());
        System.out.printf("Treatment: %-37s%n", appointment.getTreatmentType());
        
        // Print bill details
        System.out.println("\nBILL DETAILS:");
        printSeparator();
        printBillItem("Registration Fee", REGISTRATION_FEE);
        printBillItem("Treatment Price", treatmentPrice);
        printBillItem("Tax (2.5%)", tax);
        printSeparator();
        printBillItem("Total Amount", total);
        
        // Print footer
        printBorder();
        System.out.println();
        printCenteredLine("Thank you for choosing Aurora Skin Care!");
        System.out.println();
        printBorder();
    }
}