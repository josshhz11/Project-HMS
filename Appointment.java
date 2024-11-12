import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime dateTime;
    private String status;
    private Map<Medication, Integer> prescribedMedication;
    private String medicationStatus;
    private String consultationNotes;
    private static Scanner sc = new Scanner(System.in);

    public Appointment(String appointmentID, String patientID, String doctorID, LocalDateTime dateTime) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.status = "Pending"; // Confirmed, Cancelled, Completed
        // to update only after status becomes "Completed"
        this.prescribedMedication = new HashMap<>();
        this.medicationStatus = null;
        this.consultationNotes = null;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMedicationStatus() {
        return medicationStatus;
    }

    public void confirm() {
        this.status = "Confirmed";
    }

    public void cancel() {
        this.status = "Cancelled";
    }

    public void complete(Inventory inventory, String appointmentID) {
        this.status = "Completed";
        System.out.println("""
                Prescribe Medication for Appointment (if any): 
                1. Yes
                2. No
                Choose options (1-2):
                """);
        int option = sc.nextInt();
        
        switch (option) {
            case 1:
                System.out.println("Medication ID: ");
                String medicationID = sc.nextLine();
                System.out.println("Quantity: ");
                int quantity = sc.nextInt();
                Medication medication = inventory.findMedicationByID(medicationID);
                this.prescribedMedication.put(medication, quantity);
                this.medicationStatus = "Pending to Dispense";
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }

        System.out.println("Consultation Notes for Appointment (if any): ");
        this.consultationNotes = sc.nextLine();
    }

    public void completeDispense() {
        this.medicationStatus = "Dispense Complete";
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Appointment [Appointment ID=" + appointmentID + ", Patient ID=" + patientID + ", Doctor ID=" + doctorID
                + ", Date and Time=" + dateTime + ", Status=" + status + "]";
    }

    public static void closeScanner() {
        sc.close(); // Close scanner when it’s no longer needed
    }
}
