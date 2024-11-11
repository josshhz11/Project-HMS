public class Administrator extends User {
    public Administrator(String userID, String password, String name, String role) {
        super(userID, password, name, role);
    }

    // IMPORTANT
    public void viewAndManageHospitalStaff() {
        // Can put the entire view and manage options menu within here
        // Include addStaff(), updateStaff(), etc. here
    }
    
    public void addStaff(String staffID, String name, String role, String password) {
        System.out.println("Staff " + name + " with ID " + staffID + " added as " + role);
    }

    public void updateStaffRole(String staffID, String newRole) {
        System.out.println("Staff ID " + staffID + " role updated to: " + newRole);
    }

    public void removeStaff(String staffID) {
        System.out.println("Staff ID " + staffID + " has been removed.");
    }

    public void viewAllAppointments(SchedulingSystem schedulingSystem) {
        schedulingSystem.displayAvailableSlots();
    }

    // IMPORTANT
    public void viewAndManageMedicationInventory() {
        // Can put the entire view and manage options menu within here
        // Include updateInventory() below
    }
    
    public void updateInventory(Inventory inventory, String medicationName, int newStock) {
        inventory.updateStock(medicationName, newStock);
        System.out.println("Inventory updated successfully.");
    }

    public void approveReplenishmentRequest(Inventory inventory, String medicationName) {
        System.out.println("Replenishment for " + medicationName + " approved.");
    }

    @Override
    public void displayMenu() {
        System.out.println("""
                Administrator Display Menu: 
                1. View and Manage Hospital Staff
                2. View Appointment Details
                3. View and Manage Medication Inventory
                4. Approve Replenishment Requests
                5. Logout
                Choose options (1-5):
                """);
    }

    
}
