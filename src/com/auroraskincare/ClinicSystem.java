package com.auroraskincare;
import java.util.*;

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
            System.out.println("7. Complete Appointment and Generate Invoice");
            if (currentUser.getRole().equals("ADMIN")) {
                System.out.println("8. Manage Users");
            }
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: registerPatient(); break;
                case 2: makeAppointment(); break;
                case 3: viewAllAppointments(); break;
                case 4: searchAppointment(); break;
                case 5: updatePatientDetails(); break;
                case 6: viewAllPatients(); break;
                case 7: generateInvoice(); break;
                case 8: 
                    if (currentUser.getRole().equals("ADMIN")) {
                        manageUsers();
                    } else {
                        System.out.println("Invalid choice!");
                    }
                    break;
                case 9:
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

        System.out.println("\nAvailable days:");
        System.out.println("1. Monday (10:00am - 01:00pm)");
        System.out.println("2. Wednesday (02:00pm - 05:00pm)");
        System.out.println("3. Friday (04:00pm - 08:00pm)");
        System.out.println("4. Saturday (09:00am - 01:00pm)");
        System.out.print("Select day (1-4): ");
        int dayChoice = scanner.nextInt();
        scanner.nextLine();

        String date;
        String timeSlot;
        switch (dayChoice) {
            case 1: 
                date = "Monday";
                timeSlot = "10:00am - 01:00pm";
                break;
            case 2:
                date = "Wednesday";
                timeSlot = "02:00pm - 05:00pm";
                break;
            case 3:
                date = "Friday";
                timeSlot = "04:00pm - 08:00pm";
                break;
            case 4:
                date = "Saturday";
                timeSlot = "09:00am - 01:00pm";
                break;
            default:
                System.out.println("Invalid day selection!");
                return;
        }

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

        System.out.printf("\nRegistration fee: LKR %.2f%n", REGISTRATION_FEE);
        System.out.print("Confirm payment (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            Appointment appointment = new Appointment(date, timeSlot, patient, selectedDoctor, treatmentType, treatmentPrice);
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

        // Calculate total
        double treatmentPrice = appointment.getTreatmentPrice();
        double tax = Math.ceil((treatmentPrice + REGISTRATION_FEE) * TAX_RATE * 100) / 100;
        double total = treatmentPrice + REGISTRATION_FEE + tax;

        // Print invoice
        System.out.println("\n==========================================");
        System.out.println("           AURORA SKIN CARE              ");
        System.out.println("              INVOICE                    ");
        System.out.println("==========================================");
        System.out.println("Invoice Date: " + appointment.getDate());
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