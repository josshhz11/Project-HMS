public class Pharmacist extends User {
    public Pharmacist(String userID, String password, String name, String role) {
        super(userID, password, name, role);
    }

    public void viewAppointmentOutcomeRecord() {
        
    }

    // Method to update the prescription status
    public void updatePrescriptionStatus(String prescriptionID, String status) {
        System.out.println("Updating prescription status for ID: " + prescriptionID);
        System.out.println("New status: " + status);
    }
    
    // Method to view the current medication inventory
    public void viewInventory(Inventory inventory) {
        System.out.println("Viewing medication inventory:");
        inventory.displayInventory();
    }

    public void submitReplenishmentRequest() {
        
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
