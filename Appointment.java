import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime dateTime;
    private String status;
    private List<HashMap<Medication, String>> prescribedMedication; // medication map to status
    private String consultationNotes;
    private static Scanner sc = new Scanner(System.in);

    public Appointment(String appointmentID, String patientID, String doctorID, LocalDateTime dateTime) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.status = "Pending"; // Confirmed, Cancelled, Completed
        // to update only after status becomes "Completed"
        this.prescribedMedication = null;
        this.consultationNotes = null;
    }

    // Getter for doctorID
    public String getAppointmentID() {
        return appointmentID;
    }

    // Getter for doctorID
    public String getDoctorID() {
        return doctorID;
    }

    // Getter for dateTime
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void confirm() {
        this.status = "Confirmed";
    }

    public void cancel() {
        this.status = "Cancelled";
    }

    public void complete() {
        this.status = "Completed";
        System.out.println("Prescribed Medication for Appointment (if any): ");
        this.prescribedMedication = sc.nextLine(); // GO FIND OUT WHAT PRESCRIBED MEDICATION IS
        System.out.println("Consultation Notes for Appointment (if any): ");
        this.consultationNotes = sc.nextLine();
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
