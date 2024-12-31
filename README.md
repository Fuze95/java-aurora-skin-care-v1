# Aurora Skin Care Clinic Management System

A Java-based clinic management system designed for Aurora Skin Care clinic to handle patient appointments, treatments, and billing.

## Features

- **User Authentication**
  - Role-based access control (Admin/Staff)
  - Secure login system
  - User management (Admin only)

- **Patient Management**
  - Patient registration
  - Update patient information
  - View patient records

- **Appointment System**
  - Schedule appointments
  - Multiple treatment options
  - Flexible time slots (15-minute intervals)
  - View appointments by day
  - Search appointments by ID or patient name
  - Update appointment details

- **Doctor Management**
  - View doctor information
  - Doctor assignment for appointments
  - Specialization tracking

- **Billing System**
  - Automatic invoice generation
  - Registration fee handling
  - Tax calculation
  - Formatted invoice output

## Project Structure

```
Aurora-Skin-Care/
│
├── src/
│   └── com/
│       └── auroraskincare/
│           ├── Main.java
│           ├── ClinicSystem.java
│           ├── Person.java
│           ├── Patient.java
│           ├── Doctor.java
│           ├── User.java
│           ├── Appointment.java
│           └── InvoiceGenerator.java
│
└── bin/
    └── com/
        └── auroraskincare/
            ├── Main.class
            ├── ClinicSystem.class
            ├── Person.class
            ├── Patient.class
            ├── Doctor.class
            ├── User.class
            ├── Appointment.class
            └── InvoiceGenerator.class
```

## System Requirements

- Java Development Kit (JDK) 8 or higher
- Command-line interface for operation

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/Aurora-Skin-Care.git
```

2. Navigate to the project directory:
```bash
cd Aurora-Skin-Care
```

3. Compile the Java files:
```bash
javac -d bin src/com/auroraskincare/*.java
```

4. Run the application:
```bash
java -cp bin com.auroraskincare.Main
```

## Usage

### Default Login Credentials

```
Admin User:
- Username: admin
- Password: admin123

Staff User:
- Username: staff
- Password: staff123
```

### Available Treatment Options

- Acne Treatment (LKR 2,750.00)
- Skin Whitening (LKR 7,650.00)
- Mole Removal (LKR 3,850.00)
- Laser Treatment (LKR 12,500.00)

### Operating Hours

- Monday: 10:00 - 13:00
- Wednesday: 14:00 - 17:00
- Friday: 16:00 - 20:00
- Saturday: 09:00 - 13:00

## Class Structure

- `Person`: Abstract base class for all persons in the system
- `Patient`: Extends Person, manages patient information
- `Doctor`: Extends Person, handles doctor-specific details
- `User`: Extends Person, manages system users and authentication
- `Appointment`: Manages appointment details and status
- `InvoiceGenerator`: Handles invoice creation and formatting
- `ClinicSystem`: Core system functionality and menu handling
- `Main`: Entry point and primary UI loop

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
