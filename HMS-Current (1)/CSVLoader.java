
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

public class CSVLoader {
        // Save users to a file
    public static void saveUsers(List<User> users, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Load users from a file
    public static List<User> loadUsers(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found. Starting with default users.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return null;
    }

    public static void loadPatientsFromCSV(String filePath, Main mainInstance) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        boolean header = true;

        while ((line = br.readLine()) != null) {
            if (header) {
                header = false; // Skip header line
                continue;
            }

            String[] values = line.split(",");
            String patientID = values[0];
            String name = values[1];
            String dateOfBirth = values[2];
            String gender = values[3];
            String bloodType = values[4];
            String email = values[5];

            // Create a new Patient object and add it to Main's patients list
            Patient patient = new Patient(patientID, name, gender, dateOfBirth, email, bloodType);
            mainInstance.patients.add(patient); // Populate Main's patients list directly
        }
        br.close();
    }


public static void loadStaffFromCSV(String filePath, Main mainInstance) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(filePath));
    String line;
    boolean header = true;

    while ((line = br.readLine()) != null) {
        if (header) {
            header = false; // Skip header line
            continue;
        }

        String[] values = line.split(",");
        String staffID = values[0];
        String name = values[1];
        String role = values[2];
        String gender = values[3];
        int age = Integer.parseInt(values[4]);

        switch (role) {
            case "Doctor":
                String specialty = values[5];
                Doctor doctor = new Doctor(staffID, name, gender, specialty);
                mainInstance.doctors.add(doctor); // Add doctor to Main's doctors list
                break;
            case "Pharmacist":
                Pharmacist pharmacist = new Pharmacist(staffID, name, gender);
                mainInstance.pharmacists.add(pharmacist); // Add pharmacist to Main's pharmacists list
                break;
            case "Administrator":
                Administrator admin = new Administrator(staffID, name, gender);
                mainInstance.administrators.add(admin); // Add administrator to Main's administrators list
                break;
            default:
                System.out.println("Unknown role: " + role);
                break;
        }
    }
    br.close();
}

public static void loadMedicineFromCSV(String filePath, Main mainInstance) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(filePath));
    String line;
    boolean header = true;

    while ((line = br.readLine()) != null) {
        if (header) {
            header = false; // Skip header line
            continue;
        }
        
        String[] values = line.split(",");
        String medicationID = values[0];
        String name = values[1]; // Medicine Name
        int quantity = Integer.parseInt(values[2].trim());
        int lowStockAlert = Integer.parseInt(values[3].trim());

        // Create a new Medication object and add it to Main's inventory list
        Medication medication = new Medication(medicationID, name, quantity, lowStockAlert);
        mainInstance.inventory.updateInventory(medication); // Populate Main's Inventory list directly
    }
    br.close();
}

}
