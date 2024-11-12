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
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Hospital Management System!");
        User currentUser = null;

        // Login process
        while (true) {
            System.out.print("Enter User ID: ");
            String userID = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();

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
            int choice = sc.nextInt();

            if (currentUser instanceof Patient) {
                loggedIn = handlePatientOptions((Patient) currentUser, loggedIn, choice, sc);
            } else if (currentUser instanceof Doctor) {
                loggedIn = handleDoctorOptions((Doctor) currentUser, loggedIn, choice, sc);
            } else if (currentUser instanceof Pharmacist) {
                loggedIn = handlePharmacistOptions((Pharmacist) currentUser, loggedIn, choice, sc);
            } else if (currentUser instanceof Administrator) {
                loggedIn = handleAdminOptions((Administrator) currentUser, loggedIn, choice, sc);
            }
        }

        if (loggedIn == false) {
            System.out.println("Successfully logged out.");
        }

        sc.close();
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
    private static boolean handlePatientOptions(Patient patient, boolean loggedIn, int choice, Scanner sc) {
        switch (choice) {
            case 1 -> patient.viewMedicalRecord();
            case 2 -> patient.updateContactInfo();
            case 3 -> patient.viewAvailableSlots(schedulingSystem);
            case 4 -> patient.scheduleAppointment(schedulingSystem);
            case 5 -> patient.rescheduleAppointment(schedulingSystem); 
            case 6 -> patient.cancelAppointment();
            case 7 -> patient.viewScheduledAppointments(); 
            case 8 -> patient.viewPastAppointmentOutcomeRecords();
            case 9 -> {
                loggedIn = false;
                System.out.println("Logging out.");
            }
            default -> System.out.println("Invalid option. Please try again.");
        }

        return loggedIn;
    }
    
    // Handle Doctor options
    private static boolean handleDoctorOptions(Doctor doctor, boolean loggedIn, int choice, Scanner sc) {
        switch (choice) {
            case 1 -> {
                System.out.print("Enter patient ID to view medical record: ");
                String patientID = sc.nextLine();
                Patient patient = findPatientByID(patientID);
                if (patient != null) {
                    doctor.viewPatientMedicalRecord(patient);
                } else {
                    System.out.println("Patient not found.");
                }
            }
            case 2 -> {
                System.out.print("Enter patient ID to update medical record: ");
                String patientID = sc.nextLine();
                Patient patient = findPatientByID(patientID);
                doctor.updatePatientMedicalRecord(patient);
            }
            case 3 -> doctor.viewPersonalSchedule(); // TO CHECK
            case 4 -> doctor.setAvailability(); // TO FINISH
            case 5 -> doctor.respondToAppointmentRequests(); // TO FINISH
            case 6 -> doctor.viewUpcomingAppointments(); // TO FINISH
            case 7 -> doctor.recordAppointmentOutcome(inventory);
            case 8 -> {
                loggedIn = false;
                System.out.println("Logging out.");
            } 
            default -> System.out.println("Invalid option. Please try again.");
        }
        return loggedIn;
    }

    // Handle Pharmacist options
    private static boolean handlePharmacistOptions(Pharmacist pharmacist, boolean loggedIn, int choice, Scanner sc) {
        switch (choice) {
            case 1 -> pharmacist.viewAppointmentOutcomeRecord(patients);
            case 2 -> { // more or less done except menu and unavailable option
                System.out.println("Enter Appointment ID to update status: ");
                String appointmentID = sc.nextLine();
                System.out.println("""
                        Update Prescription Status:
                        1. Complete
                        2. Unavailable
                        Choose action (1-2):
                        """); // complete this menu
                int status = sc.nextInt();
                pharmacist.updatePrescriptionStatus(appointmentID, status, patients);
            }
            case 3 -> pharmacist.viewInventory(inventory);
            case 4 -> pharmacist.submitReplenishmentRequest(inventory);
            case 5 -> {
                loggedIn = false;
                System.out.println("Logging out.");
            } 
            default -> System.out.println("Invalid option. Please try again.");
        }
        return loggedIn;
    }

    // Handle Administrator options
    private static boolean handleAdminOptions(Administrator admin, boolean loggedIn, int choice, Scanner sc) {
        switch (choice) {
            case 1 -> admin.viewAndManageHospitalStaff();
            case 2 -> admin.viewAllAppointments(schedulingSystem); // VIEW APPOINTMENT DETAILS
            case 3 -> admin.viewAndManageMedicationInventory(inventory);
            case 4 -> admin.approveReplenishmentRequest(inventory);
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
    

