import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    private String doctorID;
    private String specialty;
    private List<Appointment> schedule;
    private static Scanner sc = new Scanner(System.in);

    public Doctor(String userID, String password, String name, String role, String doctorID, String specialty) {
        super(userID, password, name, role);
        this.doctorID = doctorID;
        this.specialty = specialty;
        this.schedule = new ArrayList<>();
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getSpecialty() {
        return specialty;
    }

    // View a patient's medical record
    public void viewPatientMedicalRecord(Patient patient) {
        System.out.println("Viewing medical record for Patient ID: " + patient.getPatientID());
        System.out.println(patient.getMedicalRecord());
    }

    public void updatePatientMedicalRecord(Patient patient) {
        System.out.println("Updating medical record for Patient ID: " + patient.getPatientID());
        System.out.println("""
                1. Diagnosis
                2. Treatment
                Choose options (1-2) to update:
                """);
        int option = sc.nextInt();

        switch (option) {
            case 1:
                System.out.println("Update diagnosis: ");
                String newDiagnosis = sc.nextLine();
                patient.getMedicalRecord().addDiagnosis(newDiagnosis);
                break;
            case 2:
                System.out.println("Update treatment: ");
                String newTreatment = sc.nextLine();
                patient.getMedicalRecord().addTreatment(newTreatment);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    // Update a patient's medical record with a new diagnosis
    public void updatePatientMedicalRecord(Patient patient, String diagnosis) {
        System.out.println("Updating medical record for Patient ID: " + patient.getPatientID());
        patient.getMedicalRecord().addDiagnosis(diagnosis);
        System.out.println("Diagnosis added successfully.");
    }
    
    public void viewPersonalSchedule() {
        System.out.println("Doctor Schedule:");
        for (Appointment appointment : schedule) {
            System.out.println(appointment);
        }
    }

    public void setAvailability() {

    }

    public void respondToAppointmentRequests() {

    }

    public void viewUpcomingAppointments() {

    }

    public void recordAppointmentOutcome(Inventory inventory) {
        System.out.println("Enter Appointment ID to record outcome: ");
        String appointmentID = sc.nextLine();
        for (Appointment appointment : schedule) {
            if (appointment.getAppointmentID() == appointmentID) {
                appointment.complete(inventory, appointmentID);
            }
        }
    }

    // not in use rn
    public void addAppointment(Appointment appointment) {
        schedule.add(appointment);
    }

    @Override
    public void displayMenu() {
        System.out.println("""
                Doctor Display Menu: 
                1. View Patient Medical Records
                2. Update Patient Medical Records
                3. View Personal Schedule
                4. Set Availability for Appointments
                5. Accept or Decline Appointment Requests
                6. View Upcoming Appointments
                7. Record Appointment Outcome
                8. Logout
                Choose options (1-8):
                """);
    }

    public static void closeScanner() {
        sc.close(); // Close scanner when it’s no longer needed
    }
}
