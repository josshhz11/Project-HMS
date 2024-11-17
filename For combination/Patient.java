import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {
    private String email;
    private String contactNumber;
    private String dateOfBirth;
    private String bloodType;
    private List<Appointment> appointments;
    private MedicalRecord medicalRecord;
    private static Scanner sc = new Scanner(System.in);

    // Constructor
    public Patient(String patientID, String name, String gender, String dateOfBirth, String email, String bloodType) {
        super(patientID, null, name, gender, "Patient");
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.contactNumber = "Unknown";
        this.medicalRecord = new MedicalRecord(patientID, bloodType);
        this.appointments = new ArrayList<>();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void viewPatientInformation() {
        System.out.println("""
                Which information would you like to view?
                1. Medical Record
                2. Personal Information
                Choose options (1-2): """);
        int option = sc.nextInt();

        switch (option) {
            case 1:
                System.out.println(medicalRecord);
                break;
            case 2:
                viewPersonalInformation();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                return;
        }
    }

    public void viewPersonalInformation() {
        System.out.println("Personal Information for Patient ID: " + userID);
        System.out.println("Patient Name: " + name);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Email Address: " + email);
        System.out.println();
    }

    // Update contact information
    public void updateContactInfo() {
        System.out.println("""
                Update Contact Info: 
                1. Email
                2. Contact Number
                Choose option (1-2) to update:  """);
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                System.out.print("Enter new email: ");
                this.email = sc.nextLine();
                break;
            case 2:
                System.out.print("Enter new contact number: ");
                this.contactNumber = sc.nextLine();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                return;
        }
        System.out.println("Contact information updated successfully.");
    }

    public void scheduleAppointment(SchedulingSystem schedulingSystem) {
        try {
            // Ask for specialty
            System.out.println("Select specialty:\n1. Cardiology\n2. Psychiatry\n3. Radiology\n4. Infectious Diseases");
            int specialtyOption = sc.nextInt();
            sc.nextLine(); // Clear buffer
    
            String specialty = switch (specialtyOption) {
                case 1 -> "Cardiology";
                case 2 -> "Psychiatry";
                case 3 -> "Radiology";
                case 4 -> "Infectious Diseases";
                default -> "Invalid";
                };

    
            List<Doctor> doctors = schedulingSystem.getDoctorsBySpecialty(specialty);
            if (doctors.isEmpty()) {
                System.out.println("No doctors available for the selected specialty.");
                return;
            }
    
            // Display doctors and prompt for selection
            System.out.println("Select a doctor:");
            for (int i = 0; i < doctors.size(); i++) {
                System.out.println((i + 1) + ". Dr. " + doctors.get(i).getName());
            }
    
            int doctorIndex = sc.nextInt() - 1;
            sc.nextLine(); // Clear buffer
    
            if (doctorIndex < 0 || doctorIndex >= doctors.size()) {
                System.out.println("Invalid selection. Returning to menu.");
                return;
            }
    
            Doctor selectedDoctor = doctors.get(doctorIndex);
    
            // Display available slots
            boolean hasSlots = schedulingSystem.displayAvailableSlotsForDoctor(selectedDoctor);
            if (!hasSlots) {
                System.out.println("No available slots for the selected doctor. Returning to menu.");
                return; // Exit if no slots are available
            }
    
            // Ask for the preferred time slot
            System.out.println("Enter your preferred date (yyyy-MM-dd):");
            LocalDate preferredDate = LocalDate.parse(sc.nextLine());
    
            System.out.println("Enter your preferred time (HH:mm):");
            LocalTime preferredTime = LocalTime.parse(sc.nextLine());
    
            LocalDateTime slot = LocalDateTime.of(preferredDate, preferredTime);
    
            schedulingSystem.bookSlot(this, selectedDoctor, slot, null);
    
        } catch (Exception e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
        }
    }
    
    public void cancelAppointment(SchedulingSystem schedulingSystem) {
        Appointment appointmentToCancel = selectAppointment("cancel");
        if (appointmentToCancel == null) {
            return; // If no valid appointment is selected, exit
        }
        schedulingSystem.cancelAppointment(appointmentToCancel);
    }
    
    public void rescheduleAppointment(SchedulingSystem schedulingSystem) {
        Appointment appointmentToReschedule = selectAppointment("reschedule");
        if (appointmentToReschedule == null) {
            return; // If no valid appointment is selected, exit
        }
        System.out.println("Canceling the selected appointment...");
        schedulingSystem.cancelAppointment(appointmentToReschedule);
    
        System.out.println("Proceeding to reschedule the appointment...");
        scheduleAppointment(schedulingSystem);
    }
    
    private Appointment selectAppointment(String action) {
        if (appointments.isEmpty()) {
            System.out.println("You have no appointments to " + action + ".");
            return null;
        }
    
        System.out.println("Your Appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println((i + 1) + ". " + appointments.get(i));
        }
    
        System.out.print("Enter the number of the appointment to " + action + ": ");
        int appointmentIndex = getIntInput() - 1;
    
        if (appointmentIndex >= 0 && appointmentIndex < appointments.size()) {
            return appointments.get(appointmentIndex);
        } else {
            System.out.println("Invalid selection. No appointment was " + action + "d.");
            return null;
        }
    }
    

public void viewScheduledAppointments() {
    System.out.println("\nScheduled Appointments for Patient: " + name + ", Patient ID: " + getuserID());

    List<Appointment> confirmedAppointments = new ArrayList<>();

    // Filter for confirmed appointments
    for (Appointment appointment : appointments) {
        if ("Confirmed".equalsIgnoreCase(appointment.getStatus())) {
            confirmedAppointments.add(appointment);
        }
    }

    // Sort appointments by date and time
    confirmedAppointments.sort(Comparator.comparing(Appointment::getDateTime));

    if (confirmedAppointments.isEmpty()) {
        System.out.println("You have no scheduled appointments.");
    } else {
        System.out.println("--- Appointments ---");
        for (Appointment appointment : confirmedAppointments) {
            System.out.println(
                "Date: " + appointment.getDateTime().toLocalDate() +
                ", Time: " + appointment.getDateTime().toLocalTime() +
                ", Doctor: " + appointment.getDoctorID() +
                ", Appointment ID: " + appointment.getAppointmentID() 
            );
        }
    }

    System.out.println("--------------------");
}

// NEED UPDATE THIS BASED ON MEDICINE CODE TOO, STICK W THE NICE FORMATTING FROM OPT 6!!

    public void viewPastAppointmentOutcomeRecords() {
        System.out.println("Past Appointments:");
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
                1. View Patient Information
                2. Update Personal Information 
                3. Schedule an Appointment
                4. Reschedule an Appointment
                5. Cancel an Appointment
                6. View Scheduled Appointments
                7. View Past Appointment Outcome Records (LEFT W THIS TO UPDATE)
                8. Logout
                Choose options (1-8): """);
    }

    private int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next(); // Clear invalid input
        }
        int input = sc.nextInt();
        sc.nextLine(); // Clear buffer
        return input;
    }

    //private String getStringInput() {
        //return sc.nextLine().trim();
    //}
}


