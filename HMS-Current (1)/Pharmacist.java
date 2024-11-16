import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.print.Doc;

public class Pharmacist extends User {
    private static Scanner sc = new Scanner(System.in);

    public Pharmacist(String userID, String name, String gender) {
        super(userID, null, name, gender, "Pharmacist"); // Default password is null
    }

    public String getUserID() {
        return userID;
    }


    public void viewAppointmentOutcomeRecord(List<Patient> patients) {
        // I did this manually but see if can do it a different, more efficient way.
        // Pringint out all completed appointments
        for (Patient patient : patients) {
            for (Appointment appointment : patient.getAppointments()) {
                if (appointment.getStatus() == "Completed") {
                    System.out.println(appointment.toString());
                }
            }
        }
    }
    
    public void updatePrescriptionStatus(String appointmentID, List<Patient> patients) {
        for (Patient patient : patients) {
            for (Appointment appointment : patient.getAppointments()) {
                if (appointment.getAppointmentID() == appointmentID) {
                    if (appointment.getMedicationStatus() == "Dispense Complete") {
                        System.out.println("Medication already dispensed.");
                        return;
                    } else {
                        Map<Medication, Integer> medications = appointment.getPrescribedMedication();
                        
                        if (medications.isEmpty()) {
                            System.out.println("No medications prescribed for this appointment.");
                            return;
                        }

                        for (Map.Entry<Medication, Integer> entry: medications.entrySet()) {
                            Medication medication = entry.getKey();
                            int quantity = entry.getValue();

                            if (quantity > medication.getQuantity()) {
                                System.out.println("Insufficient Medication " + medication.getName() + " in Inventory");
                            } else {
                                System.out.println("Dispensing Medication " + medication.getName());
                                medication.updateQuantity(medication.getQuantity() - quantity);
                            }
                        }

                    }
                }
            }
        }
    }

    // Method to view the current medication inventory
    public void viewInventory(Inventory inventory) {
        System.out.println("Viewing medication inventory:");
        inventory.displayInventory(false);
    }

    public void submitReplenishmentRequest(Inventory inventory) {
        inventory.displayInventory(false);
        System.out.println("Submit Replenishment Request for Medication ID: ");
        String medicationID = sc.next();
        System.out.println("Amount to replenish: ");
        int replenishAmount = sc.nextInt();
        inventory.newReplenishmentRequest(medicationID, replenishAmount);
    }

    // for the administrator function
    public void updateRole(Pharmacist pharmacist, int newRole, List<Doctor> doctors, List<Administrator> administrators) {
        switch (newRole) {
            case 1:
                this.role = "Doctor";
                System.out.println("Enter Doctor Specialty: ");
                sc.nextLine();
                String specialty = sc.nextLine();
                Doctor doctor = new Doctor(pharmacist.getuserID(), pharmacist.getName(), pharmacist.getGender(), specialty);
                doctors.add(doctor);
                break;
            case 3:
                this.role = "Administrator";
                Administrator administrator = new Administrator(pharmacist.getuserID(), pharmacist.getName(), pharmacist.getGender());
                administrators.add(administrator);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                return;
        }
    }

    // Find Pharmacist by ID
    public static Pharmacist findPharmacistByID(String pharmacistID, List<Pharmacist> pharmacists) {
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getuserID().equals(pharmacistID)) {
                return pharmacist;
            }
        }
        return null;
    }

    @Override
    public void displayMenu() {
        System.out.println("""
                
                Pharmacist Display Menu: 
                1. View Appointment Outcome Record
                2. Update Prescription Status
                3. View Medication Inventory
                4. Submit Replenishment Request
                5. Logout
                Choose options (1-5): """);

    }
}
