import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private String doctorID;
    private String specialty;
    private List<Appointment> schedule;

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

    // Method to view a patient's medical record
    public void viewPatientMedicalRecord(Patient patient) {
        System.out.println("Viewing medical record for Patient ID: " + patient.getPatientID());
        System.out.println(patient.getMedicalRecord());
    }

    // Method to update a patient's medical record with a new diagnosis
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

    public void addAppointment(Appointment appointment) {
        schedule.add(appointment);
    }

    @Override
    public void displayMenu() {
        System.out.println("Doctor Menu:");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. Accept/Decline Appointment Requests");
        System.out.println("4. View Personal Schedule");
        System.out.println("5. Logout");
        
        // mine
        System.out.println("""
                Doctor Display Menu: 
                1. View Patients
                2. View Schedule
                3. View Appointment Requests
                4. View Appointment Outcome Records
                5. Logout
                Choose options (1-5):
                """);
    }
}
