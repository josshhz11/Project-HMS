import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private List<Medication> medications;
    private static Scanner sc = new Scanner(System.in);

    public Inventory() {
        this.medications = new ArrayList<>();
    }

    public void addMedication() {
        System.out.println("Enter Medication ID: ");
        String medicationID = sc.nextLine();
        System.out.println("Enter Medication Name: ");
        String name = sc.nextLine();
        System.out.println("Enter Medication Quantity: ");
        int quantity = sc.nextInt();

        Medication newMedication = new Medication(medicationID, name, quantity);
        medications.add(newMedication);
    }

    public void updateStock(String medicationID, int newQuantity) {
        for (Medication medication : medications) {
            if (medication.getMedicationID() == medicationID) {
                medication.updateQuantity(newQuantity);
            }
        }
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Medication medication : medications) {
            System.out.println("Medication ID: " + medication.getMedicationID());
            System.out.println("Medication: " + medication.getName());
            System.out.println("Quantity: " + medication.getQuantity());
        }
    }

    public void viewReplenishmentRequests() {
        for (Medication medication : medications) {
            if (medication.getReplenishmentRequest() != 0) {
                System.out.println("Medication ID: " + medication.getMedicationID());
                System.out.println("Medication Name: " + medication.getName());
                System.out.println("Quantity: " + medication.getQuantity());
                System.out.println("Replenishment Request: " + medication.getReplenishmentRequest());
                System.out.println("-----------------------------------------------------------");
            }
        }
    }

    public void fulfillReplenishmentRequest(String medicationID) {
        for (Medication medication : medications) {
            if (medication.getMedicationID() == medicationID) {
                if (medication.getReplenishmentRequest() != 0) {
                    medication.updateQuantity(medication.getQuantity() + medication.getReplenishmentRequest());
                    medication.updateReplenishmentRequest(0);
                    System.out.println("Replenishment for Medication " + medication.getName() + " successful.");
                } else {
                    System.out.println("Error: No replenishment request.");
                }
            }
        }
    }

    public void newReplenishmentRequest(String medicationID, int quantity) {
        for (Medication medication : medications) {
            if (medication.getMedicationID() == medicationID) {
                medication.updateReplenishmentRequest(medication.getReplenishmentRequest() + quantity);
            }
        }
    }

    public static void closeScanner() {
        sc.close(); // Close scanner when it’s no longer needed
    }
}
