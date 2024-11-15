import java.util.List;
import java.util.Scanner;

public class Administrator extends User {
    private static Scanner sc = new Scanner(System.in);

    public Administrator(String userID, String name, String gender) {
        super(userID, null, name, gender, "Administrator"); // Default password is null
    }

    // IMPORTANT
    public void viewAndManageHospitalStaff(List<Doctor> doctors, List<Pharmacist> pharmacists, List<Administrator> administrators, String[] roles) {
        // Can put the entire view and manage options menu within here
        // Include addStaff(), updateStaff(), etc. here
        System.out.println("""
                Hospital Staff Menu: 
                1. View Hospital Staff
                2. Manage Hospital Staff
                Choose options (1-2): """);
        int choice1 = sc.nextInt();
        int choice2, i;

        switch(choice1) {
            case 1: // View Hospital Staff
                System.out.println(""" 
                        View Hospital Staff:
                        1. Doctors
                        2. Pharmacists
                        3. Administrators
                        Choose which hospital staff to view (1-3): """);
                choice2 = sc.nextInt();

                switch (choice2) {
                    case 1:
                        i = 1;
                        for (Doctor doctor : doctors) {
                            System.out.println(i++ + ". Doctor ID: " + doctor.getuserID() + ", Name: " + doctor.getName());
                        }
                        break;
                    case 2:
                        i = 1;
                        for (Pharmacist pharmacist : pharmacists) {
                            System.out.println(i++ + ". Pharmacist ID: " + pharmacist.getuserID() + ", Name: " + pharmacist.getName());
                        }
                        break;
                    case 3:
                        i = 1;
                        for (Administrator administrator : administrators) {
                            System.out.println(i++ + ". Administrator ID: " + administrator.getuserID() + ", Name: " + administrator.getName());
                        }
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
                break;
            case 2: // Manage Hospital Staff
                System.out.println(""" 
                        1. Add Staff
                        2. Update Staff
                        3. Remove Staff
                        Enter action (1-3): """);
                choice2 = sc.nextInt();

                switch(choice2) {
                    case 1: // Add Staff
                        addStaff(doctors, pharmacists, administrators, roles);
                        break;
                    case 2: // Update Staff
                        updateStaffRole(); // CHECK
                        break;
                    case 3: // Remove Staff
                        removeStaff(); // CHECK
                        break;
                    default:
                        System.out.println("Invalid action. Please try again.");
                        break;
                }
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
        
    }
    
    public void addStaff(List<Doctor> doctors, List<Pharmacist> pharmacists, List<Administrator> administrators, String[] roles) {
        System.out.println("Enter Staff ID: ");
        String staffID = sc.next();
        System.out.println("Enter Name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Enter Gender: ");
        String gender = sc.next();
        System.out.println("""
                Enter Staff Role:
                1. Doctor
                2. Pharmacist
                3. Administrator
                Choose options (1-3): """);
        int role = sc.nextInt();

        switch (role) {
            case 1:
                Doctor doctor = new Doctor(staffID, name, gender);
                doctors.add(doctor);
                break;
            case 2:
                Pharmacist pharmacist = new Pharmacist(staffID, name, gender);
                pharmacists.add(pharmacist);
                break;
            case 3:
                Administrator administrator = new Administrator(staffID, name, gender);
                administrators.add(administrator);
                break;
            default:
                System.out.println("Invalid Option. Please try again.");
                break;
        }

        System.out.println("Staff " + name + " with ID " + staffID + " added as " + roles[role]);
    }

    public void updateStaffRole() {
        System.out.print("Enter Staff ID: ");
        String staffID = sc.nextLine();
        System.out.print("Enter New Role (Doctor, Pharmacist, etc.): ");
        String newRole = sc.nextLine();
        System.out.println("Staff ID " + staffID + " role updated to: " + newRole);
        // NEED TO ACTUALLY UPDATE STAFF LIST
    }

    public void removeStaff() {
        System.out.print("Enter Staff ID: ");
        String staffID = sc.nextLine();
        System.out.println("Staff ID " + staffID + " has been removed.");
        // NEED TO ACTUALLY REMOVE FROM STAFF LIST
    }

    public void viewAllAppointments(SchedulingSystem schedulingSystem) {
        schedulingSystem.displayAvailableSlots();
    }

    // IMPORTANT
    public void viewAndManageMedicationInventory(Inventory inventory) {
        // Can put the entire view and manage options menu within here
        // Include updateInventory() below
        System.out.println("""
                Hospital Staff Menu: 
                1. View Medication Inventory
                2. Manage Medication Inventory
                Choose options (1-2): """);
        int choice1 = sc.nextInt();

        switch(choice1) {
            case 1: // View Medication Inventory
                // TODO
                break;
            case 2: // Manage Medication Inventory
                System.out.print("Enter Medication ID to update stock: ");
                String medicationID = sc.nextLine();
                System.out.print("Enter new stock quantity: ");
                int newQuantity = sc.nextInt();
                updateInventory(inventory, medicationID, newQuantity);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }
    
    public void updateInventory(Inventory inventory, String medicationID, int newQuantity) {
        inventory.updateStock(medicationID, newQuantity);
        System.out.println("Inventory updated successfully.");
    }

    public void approveReplenishmentRequest(Inventory inventory) {
        inventory.viewReplenishmentRequests();
        System.out.println("Medication ID to replenish: ");
        String medicationID = sc.nextLine();
        inventory.fulfillReplenishmentRequest(medicationID);
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
                Choose options (1-5): """);
    }
}

