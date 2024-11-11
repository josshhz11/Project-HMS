import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> medications;

    public Inventory() {
        medications = new HashMap<>();
    }

    public void addMedication(String name, int quantity) {
        medications.put(name, medications.getOrDefault(name, 0) + quantity);
    }

    public void updateStock(String medication, int quantity) {
        medications.put(medication, quantity);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String med : medications.keySet()) {
            System.out.println("Medication: " + med + ", Quantity: " + medications.get(med));
        }
    }
}
