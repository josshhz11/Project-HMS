import java.util.Scanner;

public abstract class User {
    protected String userID;
    protected String password;
    protected String name;
    protected String role;
    protected boolean isFirstLogin;

    // Default password for new users
    private static final String DEFAULT_PASSWORD = "defaultpass";

    public User(String userID, String password, String name, String role) {
        this.userID = userID;
        this.password = password != null ? password : DEFAULT_PASSWORD;
        this.name = name;
        this.role = role;
        this.isFirstLogin = this.password.equals(DEFAULT_PASSWORD);
    }

    // Login method with first-time login check
    public boolean login(String userID, String password) {
        if (this.userID.equals(userID) && this.password.equals(password)) {
            if (isFirstLogin) {
                System.out.println("Welcome, " + name + "! Since this is your first login, please change your password.");
                changePassword();
                isFirstLogin = false; // Set first login to false after password change
            }
            return true;
        }
        return false;
    }

    // Change password method
    public void changePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        while (newPassword.equals(password) || newPassword.length() < 6) {
            System.out.println("Password must be different from the current password and at least 6 characters long.");
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine();
        }
        this.password = newPassword;
        System.out.println("Password successfully changed!");
        scanner.close();
    }

    public abstract void displayMenu();
}
