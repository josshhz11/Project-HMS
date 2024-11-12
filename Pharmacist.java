import java.util.List;
import java.util.Scanner;

public class Pharmacist extends User {
    private static Scanner sc = new Scanner(System.in);
    
    public Pharmacist(String userID, String password, String name, String role) {
        super(userID, password, name, role);
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

    public void updatePrescriptionStatus(String appointmentID, int status, List<Patient> patients) {
        // I did this manually but see if can do it a different, more efficient way.
        // this is more of printing out the medication that hasn't been dispensed yet.
        for (Patient patient : patients) {
            for (Appointment appointment : patient.getAppointments()) {
                if (appointment.getAppointmentID() == appointmentID) {
                    if (appointment.getMedicationStatus() == "Dispense Complete") {
                        System.out.println("Medication already dispensed.");
                        return;
                    } else {
                        switch (status) {
                            case 1:
                                appointment.complete();
                                break;
                            case 2:
                                // TODO
                                break;
                            default:
                                System.out.println("Invalid action. Please try again.");
                                break;
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

    public static void closeScanner() {
        sc.close(); // Close scanner when it’s no longer needed
    }
}
