import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {
    private String patientID;
    private String email;
    private String contactNumber;
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;
    private static Scanner sc = new Scanner(System.in);

    public Patient(String userID, String password, String name, String role, String patientID, String email, String contactNumber) {
        super(userID, password, name, role);
        this.patientID = patientID;
        this.email = email;
        this.contactNumber = contactNumber;
        this.medicalRecord = new MedicalRecord(patientID);
        this.appointments = new ArrayList<>();
    }

    // Getter for patient ID
    public String getPatientID() {
        return patientID;
    }

    // Getter for medical record
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    // View medical record
    public void viewMedicalRecord() {
        System.out.println("Medical Record for Patient ID: " + patientID);
        System.out.println(medicalRecord);
    }

    // Update contact information
    public void updateContactInfo(String newEmail, String newContactNumber) {
        this.email = newEmail;
        this.contactNumber = newContactNumber;
        System.out.println("Contact information updated successfully.");
    }

    // View available slots in the scheduling system
    public void viewAvailableSlots(SchedulingSystem schedulingSystem) {
        schedulingSystem.displayAvailableSlots();
    }

    // Schedule a new appointment
    public void scheduleAppointment(SchedulingSystem schedulingSystem) {
        Appointment appointment = null;
        
        System.out.println("Enter doctor ID for appointment: ");
        String doctorID = sc.nextLine();
        Doctor selectedDoctor = schedulingSystem.getDoctorById(doctorID);
        if (selectedDoctor != null) {
            LocalDateTime appointmentTime = LocalDateTime.of(2024, 11, 3, 9, 0);  // Placeholder date/time
            appointment = new Appointment("A002", this.getPatientID(), doctorID, appointmentTime);
        }

        if (schedulingSystem.bookSlot(this, appointment)) {
            appointments.add(appointment);
            System.out.println("Appointment scheduled successfully.");
        } else {
            System.out.println("Selected slot is unavailable. Please choose another slot.");
        }
    }

    // Reschedule an appointment
    public void rescheduleAppointment(SchedulingSystem schedulingSystem) {
        Appointment oldAppointment = null, newAppointment = null;
        
        // Obtaining the old appointment
        System.out.println("Enter appointment ID: ");
        String oldAppointmentID = sc.nextLine();
        
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == oldAppointmentID) {
                oldAppointment = appointment;
            }
        }

        // Obtaining the new appointment -> since this code is repeated from the scheduleAppointment() function, see if can create a function to do this.
        System.out.println("Enter doctor ID for appointment: ");
        String doctorID = sc.nextLine();
        Doctor selectedDoctor = schedulingSystem.getDoctorById(doctorID);
        if (selectedDoctor != null) {
            // see if can obtain new date then input the date below accordingly
            LocalDateTime appointmentTime = LocalDateTime.of(2024, 11, 3, 9, 0);  // Placeholder date/time
            newAppointment = new Appointment("A002", this.getPatientID(), doctorID, appointmentTime);
        }

        if (appointments.contains(oldAppointment) && schedulingSystem.bookSlot(this, newAppointment)) {
            appointments.remove(oldAppointment);
            oldAppointment.cancel();  // Mark old appointment as canceled
            appointments.add(newAppointment);
            System.out.println("Appointment rescheduled successfully.");
        } else {
            System.out.println("Unable to reschedule. Please check availability.");
        }
    }

    // Cancel an appointment
    public void cancelAppointment() {
        Appointment oldAppointment = null;
        
        // Obtaining the appointment to cancel
        System.out.println("Enter appointment ID: ");
        String oldAppointmentID = sc.nextLine();
        
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == oldAppointmentID) {
                oldAppointment = appointment;
            }
        }
        
        if (oldAppointment != null) {
            oldAppointment.cancel();
            appointments.remove(oldAppointment);
            System.out.println("Appointment cancelled successfully.");
        } else {
            System.out.println("No matching appointment found to cancel.");
        }
    }
    
    // View scheduled appointments
    public void viewScheduledAppointments() {
        System.out.println("Upcoming Appointments:");
        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equals("Confirmed")) {
                System.out.println(appointment);
            }
        }
    }

    // View Past Appointment Outcome Records
    public void viewPastAppointmentOutcomeRecords() {
        System.out.println("Upcoming Appointments:");
        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equals("Completed")) {
                System.out.println(appointment);
            }
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("""
                Patient Display Menu: 
                1. View Medical Record
                2. Update Personal Information
                3. View Available Appointment Slots
                4. Schedule an Appointment
                5. Reschedule an Appointment
                6. Cancel an Appointment
                7. View Scheduled Appointments
                8. View Past Appointment Outcome Records
                9. Logout
                Choose options (1-9):
                """);
    }

    public static void closeScanner() {
        sc.close(); // Close scanner when it’s no longer needed
    }
}
