import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private List<Medication> medications;
    private static Scanner sc = new Scanner(System.in);

    public Inventory() {
        this.medications = new ArrayList<>();
    }

    // for the administrator
    public void addMedication() {
        System.out.println("Enter Medication ID: ");
        String medicationID = sc.next();
        System.out.println("Enter Medication Name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Enter Medication Quantity: ");
        int quantity = sc.nextInt();

        if (findMedicationByID(medicationID) != null) {
            System.out.println("Medication ID already exists. Please try again.");
        }

        for (Medication medication : medications) {
            if (medication.getName() == name) {
                System.out.println("Medication Name already exists. Please try again.");
                return;
            }
        }

        Medication newMedication = new Medication(medicationID, name, quantity, 5); // lowStockAlert as 5 as the default
        medications.add(newMedication);
        System.out.println("Medication " + newMedication.getName() + " successfully added to inventory.");
    }
    
    // for the administrator
    public void removeMedication() {
        System.out.println("Enter Medication ID: ");
        String medicationID = sc.next();

        Medication medication = findMedicationByID(medicationID);
        
        if (medication != null) {
            medications.remove(medication);
            System.out.println("Medication " + medication.getName() + " successfully removed from inventory.");
        } else {
            System.out.println("Medication does not exist in inventory. Please try again.");
            return;
        }
    }

    // for the administrator
    public void updateStock() {
        System.out.println("Enter Medication ID: ");
        String medicationID = sc.next();
        Medication medication = findMedicationByID(medicationID);

        if (medication != null) {
            System.out.println("Current Quantity for Medication " + medication.getName() + ": " + medication.getQuantity());

            System.out.println("New Quantity: ");
            int newQuantity = sc.nextInt();

            medication.updateQuantity(newQuantity);
            System.out.println("Medication " + medication.getName() + " stock successfully updated to " + medication.getQuantity() + ".");
        } else {
            System.out.println("Medication does not exist in inventory. Please try again.");
            return;
        }        
    }

    // for the administrator
    public void updateLowStockAlert() {
        System.out.println("Enter Medication ID: ");
        String medicationID = sc.next();
        Medication medication = findMedicationByID(medicationID);

        if (medication != null) {
            System.out.println("Current Low Stock Alert Level for Medication " + medication.getName() + ": " + medication.getLowStockAlert());
        
            System.out.println("New Low Stock Alert Level: ");
            int newQuantity = sc.nextInt();

            medication.updateLowStockAlert(newQuantity);
            System.out.println("Medication " + medication.getName() + " Low Stock Alert successfully updated to " + medication.getLowStockAlert() + ".");
        } else {
            System.out.println("Medication does not exist in inventory. Please try again.");
            return;
        }
    }

    public void displayInventory() {
        System.out.println("===============================================================================");
        System.out.println("                             Current Inventory                                 ");
        System.out.println("===============================================================================");
        
        // Updated header to include "Low Stock Alert"
        // Adjusted column widths to fit the new column
        System.out.printf(
            "%-15s %-20s %-10s %-15s %-15s%n",
            "Medication ID",
            "Name",
            "Quantity",
            "Replenishment",
            "Low Stock Alert"
        );
    
        System.out.println("-------------------------------------------------------------------------------");
        
        for (Medication medication : medications) {
            System.out.printf(
                "%-15s %-20s %-10d %-15d %-15s%n",
                medication.getMedicationID(),
                medication.getName(),
                medication.getQuantity(),
                medication.getReplenishmentRequest(),
                medication.getLowStockAlert()
            );
        }
        System.out.println("===============================================================================");
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
