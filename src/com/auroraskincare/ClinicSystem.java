package com.auroraskincare;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClinicSystem {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;
    private List<User> users;
    private User currentUser;
    
    private static final double TAX_RATE = 0.025;
    private static final double REGISTRATION_FEE = 500.00;
    private static final Map<String, Double> TREATMENT_PRICES = new HashMap<>();
    
    static {
        TREATMENT_PRICES.put("Acne Treatment", 2750.00);
        TREATMENT_PRICES.put("Skin Whitening", 7650.00);
        TREATMENT_PRICES.put("Mole Removal", 3850.00);
        TREATMENT_PRICES.put("Laser Treatment", 12500.00);
    }

    // Add time ranges for each day
    private static final Map<String, String[]> TIME_RANGES = new HashMap<>();
    static {
        TIME_RANGES.put("Monday", new String[]{"10:00", "13:00"});
        TIME_RANGES.put("Wednesday", new String[]{"14:00", "17:00"});
        TIME_RANGES.put("Friday", new String[]{"16:00", "20:00"});
        TIME_RANGES.put("Saturday", new String[]{"09:00", "13:00"});
    }

    // Helper method to validate time input
    private boolean isTimeInRange(String day, String time) {
        try {
            String[] range = TIME_RANGES.get(day);
            String[] timeParts = time.split(":");
            int inputHour = Integer.parseInt(timeParts[0]);
            int inputMinute = Integer.parseInt(timeParts[1]);
            
            String[] startParts = range[0].split(":");
            String[] endParts = range[1].split(":");
            
            int startHour = Integer.parseInt(startParts[0]);
            int startMinute = Integer.parseInt(startParts[1]);
            int endHour = Integer.parseInt(endParts[0]);
            int endMinute = Integer.parseInt(endParts[1]);
            
            int inputTime = inputHour * 60 + inputMinute;
            int startTime = startHour * 60 + startMinute;
            int endTime = endHour * 60 + endMinute;
            
            return inputTime >= startTime && inputTime <= endTime && inputMinute % 15 == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public ClinicSystem() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        users = new ArrayList<>();
        initializeDoctors();
        initializeUsers();
    }


    private void initializeDoctors() {
        doctors.add(new Doctor(1, "Dr. John Smith", "Dermatologist", "0771234567", "john@clinic.com"));
        doctors.add(new Doctor(2, "Dr. Sarah Johnson", "Dermatologist", "0777654321", "sarah@clinic.com"));
    }

    private void initializeUsers() {
        users.add(new User("Admin", "0771111111", "admin@clinic.com", "admin", "admin123", "ADMIN"));
        users.add(new User("Receptionist", "0772222222", "reception@clinic.com", "staff", "staff123", "STAFF"));
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Optional<User> user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();

        if (user.isPresent() && user.get().verifyPassword(password)) {
            currentUser = user.get();
            System.out.println("Welcome, " + currentUser.getName() + "!");
            return true;
        } else {
            System.out.println("Invalid credentials!");
            return false;
        }
    }

    public void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Aurora Skin Care Clinic Management System ===");
            System.out.println("1. Register New Patient");
            System.out.println("2. Make Appointment");
            System.out.println("3. View All Appointments");
            System.out.println("4. Search Appointment");
            System.out.println("5. Update Patient Details");
            System.out.println("6. View All Patients");
            System.out.println("7. Update Appointment");
            System.out.println("8. Complete Appointment and Generate Invoice");
            if (currentUser.getRole().equals("ADMIN")) {
                System.out.println("9. Manage Users");
            }
            System.out.println("10. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: registerPatient(); break;
                case 2: makeAppointment(); break;
                case 3: viewAllAppointments(); break;
                case 4: searchAppointment(); break;
                case 5: updatePatientDetails(); break;
                case 6: viewAllPatients(); break;
                case 7: updateAppointment(); break;
                case 8: generateInvoice(); break;
                case 9: 
                    if (currentUser.getRole().equals("ADMIN")) {
                        manageUsers();
                    } else {
                        System.out.println("Invalid choice!");
                    }
                    break;
                case 10:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Patient Registration ===");
        
        System.out.print("Enter NIC: ");
        String nic = scanner.nextLine();
        
        if (patients.stream().anyMatch(p -> p.getNic().equals(nic))) {
            System.out.println("Patient with this NIC already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        Patient patient = new Patient(nic, name, email, phone);
        patients.add(patient);
        System.out.println("Patient registered successfully!");
    }

    private void makeAppointment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Make Appointment ===");

        if (patients.isEmpty()) {
            System.out.println("No patients registered. Please register a patient first.");
            return;
        }

        // Select patient
        System.out.println("Select patient by NIC:");
        String nic = scanner.nextLine();
        Patient patient = patients.stream()
                .filter(p -> p.getNic().equals(nic))
                .findFirst()
                .orElse(null);

        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }

        // Select date
        System.out.println("\nAvailable days:");
        System.out.println("1. Monday (10:00 - 13:00)");
        System.out.println("2. Wednesday (14:00 - 17:00)");
        System.out.println("3. Friday (16:00 - 20:00)");
        System.out.println("4. Saturday (09:00 - 13:00)");
        System.out.print("Select day (1-4): ");
        int dayChoice = scanner.nextInt();
        scanner.nextLine();

        String date;
        switch (dayChoice) {
            case 1: date = "Monday"; break;
            case 2: date = "Wednesday"; break;
            case 3: date = "Friday"; break;
            case 4: date = "Saturday"; break;
            default:
                System.out.println("Invalid day selection!");
                return;
        }

        // Get time input from user
        System.out.println("\nEnter appointment time (HH:mm, must be in 15-minute intervals)");
        System.out.println("Available time range for " + date + ": " + 
                          TIME_RANGES.get(date)[0] + " - " + TIME_RANGES.get(date)[1]);
        System.out.print("Enter time: ");
        String time = scanner.nextLine();

        if (!isTimeInRange(date, time)) {
            System.out.println("Invalid time! Time must be within range and in 15-minute intervals.");
            return;
        }

        // Select doctor
        System.out.println("\nAvailable doctors:");
        for (Doctor doctor : doctors) {
            System.out.println(doctor.getId() + ". " + doctor.getName());
        }
        System.out.print("Select doctor (1-2): ");
        int doctorId = scanner.nextInt();
        scanner.nextLine();

        Doctor selectedDoctor = doctors.stream()
                .filter(d -> d.getId() == doctorId)
                .findFirst()
                .orElse(null);

        if (selectedDoctor == null) {
            System.out.println("Invalid doctor selection!");
            return;
        }

        // Select treatment
        System.out.println("\nAvailable treatments:");
        int i = 1;
        for (Map.Entry<String, Double> treatment : TREATMENT_PRICES.entrySet()) {
            System.out.printf("%d. %s (LKR %.2f)%n", i++, treatment.getKey(), treatment.getValue());
        }
        System.out.print("Select treatment (1-4): ");
        int treatmentChoice = scanner.nextInt();
        scanner.nextLine();

        String treatmentType = new ArrayList<>(TREATMENT_PRICES.keySet()).get(treatmentChoice - 1);
        double treatmentPrice = TREATMENT_PRICES.get(treatmentType);

        // Collect registration fee
        System.out.printf("\nRegistration fee: LKR %.2f%n", REGISTRATION_FEE);
        System.out.print("Confirm payment (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            Appointment appointment = new Appointment(date, time, patient, selectedDoctor, treatmentType, treatmentPrice);
            appointments.add(appointment);
            System.out.println("Appointment booked successfully! Appointment ID: " + appointment.getAppointmentId());
        } else {
            System.out.println("Appointment booking cancelled.");
        }
    }

    private void viewAllAppointments() {
        System.out.println("\n=== All Appointments ===");
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        for (Appointment apt : appointments) {
            System.out.println("\nAppointment ID: " + apt.getAppointmentId());
            System.out.println("Patient: " + apt.getPatient().getName());
            System.out.println("Doctor: " + apt.getDoctor().getName());
            System.out.println("Date: " + apt.getDate());
            System.out.println("Time: " + apt.getTime());
            System.out.println("Treatment: " + apt.getTreatmentType());
            System.out.println("Status: " + (apt.isCompleted() ? "Completed" : "Pending"));
        }
    }

    private void searchAppointment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Search Appointment ===");
        System.out.println("1. Search by Patient Name");
        System.out.println("2. Search by Appointment ID");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        List<Appointment> foundAppointments = new ArrayList<>();
        
        switch (choice) {
            case 1:
                System.out.print("Enter patient name: ");
                String name = scanner.nextLine();
                foundAppointments = appointments.stream()
                    .filter(a -> a.getPatient().getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                break;
            case 2:
                System.out.print("Enter appointment ID: ");
                int id = scanner.nextInt();
                appointments.stream()
                    .filter(a -> a.getAppointmentId() == id)
                    .findFirst()
                    .ifPresent(foundAppointments::add);
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        if (foundAppointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        for (Appointment apt : foundAppointments) {
            System.out.println("\nAppointment ID: " + apt.getAppointmentId());
            System.out.println("Patient: " + apt.getPatient().getName());
            System.out.println("Doctor: " + apt.getDoctor().getName());
            System.out.println("Date: " + apt.getDate());
            System.out.println("Time: " + apt.getTime());
        }
    }

    private void updateAppointment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Update Appointment ===");
        
        System.out.print("Enter appointment ID: ");
        int appointmentId = scanner.nextInt();
        scanner.nextLine();

        Appointment appointment = appointments.stream()
                .filter(a -> a.getAppointmentId() == appointmentId)
                .findFirst()
                .orElse(null);

        if (appointment == null) {
            System.out.println("Appointment not found!");
            return;
        }

        if (appointment.isCompleted()) {
            System.out.println("Cannot update completed appointment!");
            return;
        }

        boolean continueUpdating = true;
        while (continueUpdating) {
            // Show current appointment details
            System.out.println("\nCurrent Appointment Details:");
            System.out.println("Doctor: " + appointment.getDoctor().getName());
            System.out.println("Day: " + appointment.getDate());
            System.out.println("Time: " + appointment.getTime());
            System.out.println("Treatment: " + appointment.getTreatmentType() + 
                             " (LKR " + appointment.getTreatmentPrice() + ")");
            
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Doctor");
            System.out.println("2. Schedule (Day and Time)");
            System.out.println("3. Treatment");
            System.out.println("4. Return to Main Menu");
            System.out.print("Select option (1-4): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Update doctor
                    System.out.println("\nAvailable doctors:");
                    for (Doctor doctor : doctors) {
                        System.out.println(doctor.getId() + ". " + doctor.getName());
                    }
                    System.out.print("Select new doctor (1-2): ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine();

                    Doctor newDoctor = doctors.stream()
                            .filter(d -> d.getId() == doctorId)
                            .findFirst()
                            .orElse(null);

                    if (newDoctor != null) {
                        appointment.setDoctor(newDoctor);
                        System.out.println("\nDoctor updated successfully!");
                        System.out.println("=================================");
                    } else {
                        System.out.println("\nInvalid doctor selection!");
                        System.out.println("=================================");
                    }
                    break;

                case 2:
                    // Update schedule (day and time together)
                    System.out.println("\nAvailable days and time slots:");
                    System.out.println("1. Monday (10:00 - 13:00)");
                    System.out.println("2. Wednesday (14:00 - 17:00)");
                    System.out.println("3. Friday (16:00 - 20:00)");
                    System.out.println("4. Saturday (09:00 - 13:00)");
                    System.out.print("Select new day (1-4): ");
                    int dayChoice = scanner.nextInt();
                    scanner.nextLine();

                    String newDate;
                    switch (dayChoice) {
                        case 1: newDate = "Monday"; break;
                        case 2: newDate = "Wednesday"; break;
                        case 3: newDate = "Friday"; break;
                        case 4: newDate = "Saturday"; break;
                        default:
                            System.out.println("\nInvalid day selection!");
                            System.out.println("=================================");
                            continue;
                    }

                    System.out.println("\nEnter new appointment time (HH:mm, must be in 15-minute intervals)");
                    System.out.println("Available time range for " + newDate + ": " + 
                                      TIME_RANGES.get(newDate)[0] + " - " + 
                                      TIME_RANGES.get(newDate)[1]);
                    System.out.print("Enter new time: ");
                    String newTime = scanner.nextLine();

                    if (isTimeInRange(newDate, newTime)) {
                        appointment.setDate(newDate);
                        appointment.setTime(newTime);
                        System.out.println("\nSchedule updated successfully!");
                        System.out.println("=================================");
                    } else {
                        System.out.println("\nInvalid time! Time must be within range and in 15-minute intervals.");
                        System.out.println("=================================");
                    }
                    break;

                case 3:
                    // Update treatment
                    System.out.println("\nAvailable treatments:");
                    int i = 1;
                    for (Map.Entry<String, Double> treatment : TREATMENT_PRICES.entrySet()) {
                        System.out.printf("%d. %s (LKR %.2f)%n", i++, treatment.getKey(), treatment.getValue());
                    }
                    System.out.print("Select new treatment (1-4): ");
                    int treatmentChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (treatmentChoice >= 1 && treatmentChoice <= TREATMENT_PRICES.size()) {
                        String newTreatmentType = new ArrayList<>(TREATMENT_PRICES.keySet()).get(treatmentChoice - 1);
                        double newTreatmentPrice = TREATMENT_PRICES.get(newTreatmentType);
                        
                        appointment.setTreatmentType(newTreatmentType);
                        appointment.setTreatmentPrice(newTreatmentPrice);
                        System.out.println("\nTreatment updated successfully!");
                        System.out.println("=================================");
                    } else {
                        System.out.println("\nInvalid treatment selection!");
                        System.out.println("=================================");
                    }
                    break;

                case 4:
                    continueUpdating = false;
                    System.out.println("\nReturning to main menu...");
                    System.out.println("=================================");
                    break;

                default:
                    System.out.println("\nInvalid choice!");
                    System.out.println("=================================");
                    break;
            }
        }
    }



    private void updatePatientDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Update Patient Details ===");
        
        System.out.print("Enter patient NIC: ");
        String nic = scanner.nextLine();

        Patient patient = patients.stream()
                .filter(p -> p.getNic().equals(nic))
                .findFirst()
                .orElse(null);

        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }

        System.out.println("Current details:");
        System.out.println("1. Name: " + patient.getName());
        System.out.println("2. Email: " + patient.getEmail());
        System.out.println("3. Phone: " + patient.getPhoneNumber());
        System.out.print("Select field to update (1-3): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                patient.setName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new email: ");
                patient.setEmail(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new phone number: ");
                patient.setPhoneNumber(scanner.nextLine());
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.println("Patient details updated successfully!");
    }

    private void viewAllPatients() {
        System.out.println("\n=== All Patients ===");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        for (Patient patient : patients) {
            System.out.println("\nNIC: " + patient.getNic());
            System.out.println("Name: " + patient.getName());
            System.out.println("Email: " + patient.getEmail());
            System.out.println("Phone: " + patient.getPhoneNumber());
        }
    }

    private void generateInvoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Generate Invoice ===");
        
        System.out.print("Enter appointment ID: ");
        int appointmentId = scanner.nextInt();
        scanner.nextLine();

        Appointment appointment = appointments.stream()
                .filter(a -> a.getAppointmentId() == appointmentId)
                .findFirst()
                .orElse(null);

        if (appointment == null) {
            System.out.println("Appointment not found!");
            return;
        }

        if (appointment.isCompleted()) {
            System.out.println("Invoice has already been generated for this appointment!");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Calculate total
        double treatmentPrice = appointment.getTreatmentPrice();
        double tax = Math.ceil((treatmentPrice + REGISTRATION_FEE) * TAX_RATE * 100) / 100;
        double total = treatmentPrice + REGISTRATION_FEE + tax;

        // Print invoice
        System.out.println("\n==========================================");
        System.out.println("           AURORA SKIN CARE              ");
        System.out.println("              INVOICE                    ");
        System.out.println("==========================================");
        System.out.println("Generated Date: " + now.format(dateFormatter));
        System.out.println("Generated Time: " + now.format(timeFormatter));
        System.out.println("Appointment Date: " + appointment.getDate());
        System.out.println("Appointment Time: " + appointment.getTime());
        System.out.println("Appointment ID: " + appointment.getAppointmentId());
        System.out.println("------------------------------------------");
        System.out.println("Patient Details:");
        System.out.println("Name: " + appointment.getPatient().getName());
        System.out.println("NIC: " + appointment.getPatient().getNic());
        System.out.println("------------------------------------------");
        System.out.println("Doctor: " + appointment.getDoctor().getName());
        System.out.println("Treatment: " + appointment.getTreatmentType());
        System.out.println("------------------------------------------");
        System.out.println("Bill Details:");
        System.out.printf("Registration Fee:      LKR %.2f%n", REGISTRATION_FEE);
        System.out.printf("Treatment Price:       LKR %.2f%n", treatmentPrice);
        System.out.printf("Tax (2.5%%):           LKR %.2f%n", tax);
        System.out.println("------------------------------------------");
        System.out.printf("Total Amount:          LKR %.2f%n", total);
        System.out.println("==========================================");
        System.out.println("Thank you for choosing Aurora Skin Care!");
        System.out.println("==========================================");

        appointment.setCompleted(true);
        System.out.println("\nInvoice generated successfully!");
    }

    private void manageUsers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Manage Users ===");
        System.out.println("1. View All Users");
        System.out.println("2. Add New User");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewAllUsers();
                break;
            case 2:
                addNewUser();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void viewAllUsers() {
        System.out.println("\n=== All Users ===");
        for (User user : users) {
            System.out.println("\nUsername: " + user.getUsername());
            System.out.println("Name: " + user.getName());
            System.out.println("Role: " + user.getRole());
            System.out.println("Email: " + user.getEmail());
        }
    }

    private void addNewUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Add New User ===");
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter role (ADMIN/STAFF): ");
        String role = scanner.nextLine().toUpperCase();

        users.add(new User(name, phone, email, username, password, role));
        System.out.println("User added successfully!");
    }
}