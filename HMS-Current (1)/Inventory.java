import java.util.ArrayList;
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
        String medicationID = sc.next();
        System.out.println("Enter Medication Name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Enter Medication Quantity: ");
        int quantity = sc.nextInt();

        Medication newMedication = new Medication(medicationID, name, quantity, 5); // lowStockAlert as 5 as the default
        medications.add(newMedication);
        System.out.println("Medication " + newMedication.getName() + " successfully added to inventory.");
    }

    public void removeMedication() {
        System.out.println("Enter Medication ID: ");
        String medicationID = sc.next();
        System.out.println("Enter Medication Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        Medication medication = findMedicationByID(medicationID);
        medications.remove(medication);
        System.out.println("Medication " + medication.getName() + " successfully removed from inventory.");
    }

    public void updateStock() {
        System.out.println("Enter Medication ID: ");
        String medicationID = sc.next();
        System.out.println("Enter Medication Name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("New Quantity: ");
        int newQuantity = sc.nextInt();

        Medication medication = findMedicationByID(medicationID);
        medication.updateQuantity(newQuantity);
        System.out.println("Inventory updated successfully.");
    }

    public void displayInventory() {
        System.out.println("=============================================================");
        System.out.println("                      Current Inventory                      ");
        System.out.println("=============================================================");
        System.out.printf("%-15s %-20s %-10s %-15s%n", "Medication ID", "Name", "Quantity", "Replenishment");
        System.out.println("-------------------------------------------------------------");
        
        for (Medication medication : medications) {
            System.out.printf(
                "%-15s %-20s %-10d %-15d%n",
                medication.getMedicationID(),
                medication.getName(),
                medication.getQuantity(),
                medication.getReplenishmentRequest()
            );
        }
        System.out.println("=============================================================");
    }

    public void updateInventory(Medication medication) {
        this.medications.add(medication);
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

    public Medication findMedicationByID(String medicationID) {
        for (Medication medication : medications) {
            if (medication.getMedicationID().equals(medicationID)) {
                return medication;
            }
        }
        return null;
    }
}
