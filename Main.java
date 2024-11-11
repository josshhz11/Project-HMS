import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Patient> patients = new ArrayList<>();
    private static List<Doctor> doctors = new ArrayList<>();
    private static List<Pharmacist> pharmacists = new ArrayList<>();
    private static List<Administrator> administrators = new ArrayList<>();
    private static Inventory inventory = new Inventory();
    private static SchedulingSystem schedulingSystem = new SchedulingSystem();

    public static void main(String[] args) {
        loadCSVData();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Hospital Management System!");
        User currentUser = null;

        // Login process
        while (true) {
            System.out.print("Enter User ID: ");
            String userID = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            currentUser = authenticateUser(userID, password);

            if (currentUser != null) {
                System.out.println("Login successful. Welcome, " + currentUser.name + "!");
                break;
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }

        boolean loggedIn = true;
        while (loggedIn) {
            currentUser.displayMenu();
            int choice = scanner.nextInt();

            if (currentUser instanceof Patient) {
                loggedIn = handlePatientOptions((Patient) currentUser, loggedIn, choice, scanner);
            } else if (currentUser instanceof Doctor) {
                loggedIn = handleDoctorOptions((Doctor) currentUser, loggedIn, choice, scanner);
            } else if (currentUser instanceof Pharmacist) {
                loggedIn = handlePharmacistOptions((Pharmacist) currentUser, loggedIn, choice, scanner);
            } else if (currentUser instanceof Administrator) {
                loggedIn = handleAdminOptions((Administrator) currentUser, loggedIn, choice, scanner);
            }
        }

        if (loggedIn == false) {
            System.out.println("Successfully logged out.");
        }

        scanner.close();
    }

    // Load data from CSV files
    private static void loadCSVData() {
        try {
            loadPatientsFromCSV("Patient_List.csv");
            loadDoctorsFromCSV("Staff_List.csv");
            loadPharmacistsFromCSV("Pharmacist_List.csv");
            loadAdministratorsFromCSV("Admin_List.csv");
            loadInventoryFromCSV("Medicine_List.csv");
        } catch (IOException e) {
            System.out.println("Error loading data from CSV files: " + e.getMessage());
        }
    }

    // Load patients from CSV
    private static void loadPatientsFromCSV(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        boolean header = true;

        while ((line = br.readLine()) != null) {
            if (header) {
                header = false; // Skip header line
                continue;
            }

            String[] values = line.split(",");
            String patientID = values[0];  // Assuming patient ID is in the first column
            String password = values[1];
            String name = values[2];
            String email = values[3];
            String contactNumber = values[4];

            patients.add(new Patient(patientID, password, name, "Patient", patientID, email, contactNumber));
        }
        br.close();
    }

    // Load doctors from CSV
    private static void loadDoctorsFromCSV(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        boolean header = true;

        while ((line = br.readLine()) != null) {
            if (header) {
                header = false; // Skip header line
                continue;
            }

            String[] values = line.split(",");
            String doctorID = values[0];  // Assuming doctor ID is in the first column
            String password = values[1];
            String name = values[2];
            String specialty = values[3];

            Doctor doctor = new Doctor(doctorID, password, name, "Doctor", doctorID, specialty);
            doctors.add(doctor);
            schedulingSystem.addDoctor(doctor);
        }
        br.close();
    }

    // Similar methods for loading pharmacists and administrators
    private static void loadPharmacistsFromCSV(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        boolean header = true;

        while ((line = br.readLine()) != null) {
            if (header) {
                header = false; // Skip header line
                continue;
            }

            String[] values = line.split(",");
            String pharmacistID = values[0];
            String password = values[1];
            String name = values[2];

            pharmacists.add(new Pharmacist(pharmacistID, password, name, "Pharmacist"));
        }
        br.close();
    }

    private static void loadAdministratorsFromCSV(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        boolean header = true;

        while ((line = br.readLine()) != null) {
            if (header) {
                header = false; // Skip header line
                continue;
            }

            String[] values = line.split(",");
            String adminID = values[0];
            String password = values[1];
            String name = values[2];

            administrators.add(new Administrator(adminID, password, name, "Administrator"));
        }
        br.close();
    }

    private static void loadInventoryFromCSV(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        boolean header = true;

        while ((line = br.readLine()) != null) {
            if (header) {
                header = false; // Skip header line
                continue;
            }

            String[] values = line.split(",");
            String medicationName = values[0];
            int quantity = Integer.parseInt(values[1]);

            inventory.addMedication(medicationName, quantity);
        }
        br.close();
    }

    // Authentication process
    private static User authenticateUser(String userID, String password) {
        for (Patient patient : patients) {
            if (patient.login(userID, password)) return patient;
        }
        for (Doctor doctor : doctors) {
            if (doctor.login(userID, password)) return doctor;
        }
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.login(userID, password)) return pharmacist;
        }
        for (Administrator admin : administrators) {
            if (admin.login(userID, password)) return admin;
        }
        return null;
    }

    // Handle Patient options
    private static boolean handlePatientOptions(Patient patient, boolean loggedIn, int choice, Scanner scanner) {
        switch (choice) {
            case 1 -> patient.viewMedicalRecord();
            case 2 -> {
                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                System.out.print("Enter new contact number: ");
                String newContact = scanner.nextLine();
                patient.updateContactInfo(newEmail, newContact);
            }
            case 3 -> patient.viewAvailableSlots(schedulingSystem);
            case 4 -> {
                System.out.print("Enter doctor ID for appointment: ");
                String doctorID = scanner.nextLine();
                Doctor selectedDoctor = schedulingSystem.getDoctorById(doctorID);
                if (selectedDoctor != null) {
                    LocalDateTime appointmentTime = LocalDateTime.of(2024, 11, 3, 9, 0);  // Placeholder date/time
                    Appointment newAppointment = new Appointment("A002", patient.getPatientID(), doctorID, appointmentTime);
                    patient.scheduleAppointment(newAppointment, schedulingSystem);
                } else {
                    System.out.println("Doctor not found.");
                }
            }
            case 5 -> patient.rescheduleAppointments(); // TO FINISH
            case 6 -> patient.cancelAppointment(); // TO FINISH
            case 7 -> patient.viewScheduledAppointments(); // TO FINISH
            case 8 -> patient.viewPastAppointmentOutcomeRecords(); // TO FINISH
            case 9 -> {
                loggedIn = false;
                System.out.println("Logging out.");
            }
            default -> System.out.println("Invalid option. Please try again.");
        }

        return loggedIn;
    }
    
    // Handle Doctor options
    private static boolean handleDoctorOptions(Doctor doctor, boolean loggedIn, int choice, Scanner scanner) {
        switch (choice) {
            case 1 -> {
                System.out.print("Enter patient ID to view medical record: ");
                String patientID = scanner.nextLine();
                Patient patient = findPatientByID(patientID);
                if (patient != null) {
                    doctor.viewPatientMedicalRecord(patient);
                } else {
                    System.out.println("Patient not found.");
                }
            }
            case 2 -> {
                System.out.print("Enter patient ID to update medical record: ");
                String patientID = scanner.nextLine();
                Patient patient = findPatientByID(patientID);
                if (patient != null) {
                    System.out.print("Enter new diagnosis: ");
                    String diagnosis = scanner.nextLine();
                    doctor.updatePatientMedicalRecord(patient, diagnosis);
                } else {
                    System.out.println("Patient not found.");
                }
            }
            case 3 -> doctor.viewPersonalSchedule(); // TO CHECK
            case 4 -> doctor.setAvailability(); // TO FINISH
            case 5 -> doctor.respondToAppointmentRequests(); // TO FINISH
            case 6 -> doctor.viewUpcomingAppointments(); // TO FINISH
            case 7 -> doctor.recordAppointmentOutcome(); // TO FINISH
            case 8 -> {
                loggedIn = false;
                System.out.println("Logging out.");
            } 
            default -> System.out.println("Invalid option. Please try again.");
        }
        return loggedIn;
    }

    // Handle Pharmacist options
    private static boolean handlePharmacistOptions(Pharmacist pharmacist, boolean loggedIn, int choice, Scanner scanner) {
        switch (choice) {
            case 1 -> pharmacist.viewInventory(inventory);
            case 2 -> {
                System.out.print("Enter Prescription ID to update status: ");
                String prescriptionID = scanner.nextLine();
                System.out.print("Enter new status (e.g., 'dispensed'): ");
                String status = scanner.nextLine();
                pharmacist.updatePrescriptionStatus(prescriptionID, status);
            }
            case 3 -> {
                loggedIn = false;
                System.out.println("Logging out.");
            } 
            default -> System.out.println("Invalid option. Please try again.");
        }
        return loggedIn;
    }

    // Handle Administrator options
    private static boolean handleAdminOptions(Administrator admin, boolean loggedIn, int choice, Scanner scanner) {
        switch (choice) {
            case 1 -> {
                System.out.print("Enter action (add/update/remove) for staff: ");
                String action = scanner.nextLine();
                System.out.print("Enter Staff ID: ");
                String staffID = scanner.nextLine();
                if (action.equalsIgnoreCase("add")) {
                    System.out.print("Enter Staff Role (Doctor, Pharmacist, etc.): ");
                    String role = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter default Password: ");
                    String password = scanner.nextLine();
                    admin.addStaff(staffID, name, role, password);
                } else if (action.equalsIgnoreCase("update")) {
                    System.out.print("Enter New Role (Doctor, Pharmacist, etc.): ");
                    String role = scanner.nextLine();
                    admin.updateStaffRole(staffID, role);
                } else if (action.equalsIgnoreCase("remove")) {
                    admin.removeStaff(staffID);
                } else {
                    System.out.println("Invalid action. Use add/update/remove.");
                }
            }
            case 2 -> admin.viewAllAppointments(schedulingSystem);
            case 3 -> {
                System.out.print("Enter medication to update: ");
                String medication = scanner.nextLine();
                System.out.print("Enter new stock quantity: ");
                int stock = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                admin.updateInventory(inventory, medication, stock);
            }
            case 4 -> {
                System.out.print("Enter Medication to approve replenishment for: ");
                String medication = scanner.nextLine();
                admin.approveReplenishmentRequest(inventory, medication);
            }
            case 5 -> {
                loggedIn = false;
                System.out.println("Logging out.");
            } 
            default -> System.out.println("Invalid option. Please try again.");
        }
        return loggedIn;
    }
    
    // Find Patient by ID
    private static Patient findPatientByID(String patientID) {
        for (Patient patient : patients) {
            if (patient.getPatientID().equals(patientID)) {
                return patient;
            }
        }
        return null;
    }
    }
    

