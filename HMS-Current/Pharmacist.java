import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Pharmacist extends User {
    private static Scanner sc = new Scanner(System.in);

    public Pharmacist(String userID, String name, String gender) {
        super(userID, null, name, gender, "Pharmacist"); // Default password is null
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
        inventory.displayInventory();
    }

    public void submitReplenishmentRequest(Inventory inventory) {
        inventory.displayInventory();
        System.out.println("Submit Replenishment Request for Medication ID: ");
        String medicationID = sc.nextLine();
        System.out.println("Amount to replenish: ");
        int replenishAmount = sc.nextInt();
        inventory.newReplenishmentRequest(medicationID, replenishAmount);
        System.out.println("Replenishment Request Submitted.");
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
                Choose options (1-5):
                """);

    }
}
