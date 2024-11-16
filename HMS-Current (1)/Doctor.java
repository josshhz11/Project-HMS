import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    private String specialty;
    private List<Appointment> schedule;
    private static Scanner sc = new Scanner(System.in);

    // Constructor updated to exclude password
    public Doctor(String userID, String name, String gender, String specialty) {
        super(userID, null, name, gender, "Doctor"); // Default password is null
        this.specialty = specialty; // Cardiology, Psychiatry, Radiology, Infectious Diseases
        this.schedule = new ArrayList<>();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }


    // View a patient's medical record
    public void viewPatientMedicalRecord(Patient patient) {
        System.out.println("Viewing medical record for Patient ID: " + patient.getuserID());
        System.out.println(patient.getMedicalRecord().toString());
    }

    public void updatePatientMedicalRecord(Patient patient) {
        System.out.println("Updating medical record for Patient ID: " + patient.getuserID());
        System.out.println("""
                1. Diagnosis
                2. Treatment
                Choose options (1-2) to update: """);
        int option = sc.nextInt();

        switch (option) {
            case 1:
                System.out.println("Update diagnosis: ");
                sc.nextLine();
                String newDiagnosis = sc.nextLine();
                patient.getMedicalRecord().addDiagnosis(newDiagnosis);
                break;
            case 2:
                System.out.println("Update treatment: ");
                sc.nextLine();
                String newTreatment = sc.nextLine();
                patient.getMedicalRecord().addTreatment(newTreatment);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }
    
    public void viewPersonalSchedule() {
        System.out.println("Doctor Schedule:");
        for (Appointment appointment : schedule) {
            System.out.println(appointment);
        }
    }

    public void setAvailability() {

    }

    public void viewAppointmentRequests() {
        for (Appointment appointment : schedule) {
            if (appointment.getStatus() == "Pending") {
                System.out.println(appointment.toString());
            }
        }
    }

    // THIS ONE MIGHT BE WRONG BECAUSE APPOINTMENT REQUESTS MAY NOT FALL UNDER SCHEDULE
    public void respondToAppointmentRequests() {
        viewAppointmentRequests();
        System.out.println("Enter Appointment ID to Respond to Request");
        String appointmentID = sc.next();
        for (Appointment appointment : schedule) {
            if (appointment.getAppointmentID() == appointmentID) {
                if (appointment.getStatus() == "Pending") {
                    System.out.println("Respond to Appointment ID: " + appointmentID);
                    System.out.println("""
                            1. Accept
                            2. Reject
                            Choose options (1-2): """);
                    int option = sc.nextInt();
                    
                    switch (option) {
                        case 1:
                            appointment.confirm();
                            System.out.println("Appointment confirmed.");
                            break;
                        case 2: 
                            appointment.cancel();
                            System.out.println("Appointment cancelled.");
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                }
            }
        }
    }

    public void viewUpcomingAppointments() {

    }

    public void recordAppointmentOutcome(Inventory inventory) {
        System.out.println("Enter Appointment ID to record outcome: ");
        String appointmentID = sc.next();
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

    // for the administrator function
    public void updateRole(Doctor doctor, int newRole, List<Pharmacist> pharmacists, List<Administrator> administrators) {
        switch (newRole) {
            case 2:
                this.role = "Pharmacist";
                Pharmacist pharmacist = new Pharmacist(doctor.getuserID(), doctor.getName(), doctor.getGender());
                pharmacists.add(pharmacist);
                break;
            case 3:
                this.role = "Administrator";
                Administrator administrator = new Administrator(doctor.getuserID(), doctor.getName(), doctor.getGender());
                administrators.add(administrator);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                return;
        }
    }

    // Find Doctor by ID
    public static Doctor findDoctorByID(String doctorID, List<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            if (doctor.getuserID().equals(doctorID)) {
                return doctor;
            }
        }
        return null;
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
                Choose options (1-8): """);
    }
}
