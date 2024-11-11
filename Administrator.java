import java.util.Scanner;

public class Administrator extends User {
    private static Scanner sc = new Scanner(System.in);
    
    public Administrator(String userID, String password, String name, String role) {
        super(userID, password, name, role);
    }

    // IMPORTANT
    public void viewAndManageHospitalStaff() {
        // Can put the entire view and manage options menu within here
        // Include addStaff(), updateStaff(), etc. here
        System.out.println("""
                Hospital Staff Menu: 
                1. View Hospital Staff
                2. Manage Hospital Staff
                Choose options (1-2):
                """);
        int choice1 = sc.nextInt();

        switch(choice1) {
            case 1: // View Hospital Staff
                // TODO
                break;
            case 2: // Manage Hospital Staff
                System.out.println(""" 
                        1. Add Staff
                        2. Update Staff
                        3. Remove Staff
                        Enter action (1-3):
                    """);
                int choice2 = sc.nextInt();

                switch(choice2) {
                    case 1: // Add Staff
                        addStaff(); // CHECK
                        break;
                    case 2: // Update Staff
                        updateStaff(); // CHECK
                        break;
                    case 3: // Remove Staff
                        removeStaff(); // CHECK
                        break;
                }
                break;
        }
        
        
        
    }
    
    public void addStaff(String staffID, String name, String role, String password) {
        System.out.print("Enter Staff ID: ");
        System.out.print("Enter Staff Role (Doctor, Pharmacist, etc.): ");
        String role = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter default Password: ");
        String password = sc.nextLine();
        System.out.println("Staff " + name + " with ID " + staffID + " added as " + role);
        // NEED TO ACTUALLY ADD TO STAFF LIST
    }

    public void updateStaffRole(String staffID, String newRole) {
        System.out.print("Enter Staff ID: ");
        System.out.print("Enter New Role (Doctor, Pharmacist, etc.): ");
        String role = sc.nextLine();
        System.out.println("Staff ID " + staffID + " role updated to: " + newRole);
        // NEED TO ACTUALLY UPDATE STAFF LIST
    }

    public void removeStaff(String staffID) {
        System.out.print("Enter Staff ID: ");
        System.out.println("Staff ID " + staffID + " has been removed.");
        // NEED TO ACTUALLY REMOVE FROM STAFF LIST
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

    // I NEED A CLOSE SCANNER FUNCTION
    
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
