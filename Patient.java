import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private String patientID;
    private String email;
    private String contactNumber;
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;

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
    public void scheduleAppointment(Appointment appointment, SchedulingSystem schedulingSystem) {
        if (schedulingSystem.bookSlot(this, appointment)) {
            appointments.add(appointment);
            System.out.println("Appointment scheduled successfully.");
        } else {
            System.out.println("Selected slot is unavailable. Please choose another slot.");
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

    // Reschedule an appointment
    public void rescheduleAppointment(Appointment oldAppointment, Appointment newAppointment, SchedulingSystem schedulingSystem) {
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
    public void cancelAppointment(Appointment appointment) {
        if (appointments.contains(appointment)) {
            appointment.cancel();
            appointments.remove(appointment);
            System.out.println("Appointment canceled successfully.");
        } else {
            System.out.println("No matching appointment found to cancel.");
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("""
                Patient Display Menu: 
                1. View Medical Record
                2. Update Contact Information
                3. View Available Appointment Slots
                4. Schedule Appointment
                5. View Scheduled Appointments
                6. Reschedule Appointment
                7. Cancel Appointment
                8. Logout
                Choose options (1-8):
                """);
    }
}
