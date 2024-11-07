import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

interface UserInterface {
    void login();
    void register();
}

class User implements UserInterface {
    protected String fullName;
    protected String email;
    protected String password;

    public User() {
    }

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    @Override
    public void login() {
        System.out.println("Logging in as user...");
    }

    @Override
    public void register() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter full name: ");
        fullName = scanner.nextLine();

        System.out.print("Enter email: ");
        email = scanner.nextLine();
        while (!Pattern.matches(".+@.+\\..+", email)) {
            System.out.print("Invalid email. Please enter again: ");
            email = scanner.nextLine();
        }

        System.out.print("Enter password (at least 6 characters with letters and numbers): ");
        password = scanner.nextLine();
        while (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", password)) {
            System.out.print("Invalid password. Please enter again: ");
            password = scanner.nextLine();
        }

        System.out.println("Registration successful!");
    }
}

class Admin extends User {
    public Admin(String fullName, String email, String password) {
        super(fullName, email, password);
    }

    public void deleteAllPasswords(Map<String, User> users) {
        users.clear();
        System.out.println("All user passwords have been deleted.");
    }

    public void editAllPasswords(Map<String, User> users) {
        Scanner scanner = new Scanner(System.in);
        for (Map.Entry<String, User> entry : users.entrySet()) {
            System.out.print("Enter new password for user " + entry.getKey() + ": ");
            String newPassword = scanner.nextLine();
            entry.getValue().password = newPassword;
        }
        System.out.println("All user passwords have been updated.");
    }

    public void viewAllPasswords(Map<String, User> users) {
        System.out.println("Viewing all user passwords:");
        for (Map.Entry<String, User> entry : users.entrySet()) {
            System.out.println("User: " + entry.getKey() + ", Password: " + entry.getValue().password);
        }
    }
}

class PasswordManager {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, User> users = new HashMap<>();
    private static final Admin admin = new Admin("Admin", "sjoyshree90@gmail.com", "admin123");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nSelect Category:");
            System.out.println("1. Website\n2. Desktop Application\n3. Game\n4. Exit");
            int categoryChoice = Integer.parseInt(scanner.nextLine());

            if (categoryChoice == 4) break;

            System.out.println("1. Login\n2. Registration");
            int actionChoice = Integer.parseInt(scanner.nextLine());

            if (actionChoice == 1) {
                login();
            } else if (actionChoice == 2) {
                register();
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (username.equals(admin.fullName) && password.equals(admin.password)) {
            System.out.println("Admin logged in successfully.");
            adminMenu();
        } else if (users.containsKey(username) && users.get(username).password.equals(password)) {
            System.out.println("User logged in successfully.");
            userMenu();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void register() {
        System.out.println("User name must valid email address with @ ");
        System.out.println("Password Must with at least 6 characters with numbers and Letter ");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
            return;
        }

        User newUser = new User();
        newUser.register();
        users.put(username, newUser);
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Add Password\n2. Edit Password\n3. Delete Password\n4. Logout");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 4) break;

            switch (choice) {
                case 1:
                    addPassword();
                    break;
                case 2:
                    editPassword();
                    break;
                case 3:
                    deletePassword();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addPassword() {
        System.out.print("Enter website/application name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password for " + name + ": ");
        String password = scanner.nextLine();

        // Add the password (could be added to a separate map for specific user-password management)
        System.out.println("Password added for " + name + ".");
    }

    private static void editPassword() {
        System.out.print("Enter website/application name to edit password: ");
        String name = scanner.nextLine();
        System.out.print("Enter new password for " + name + ": ");
        String newPassword = scanner.nextLine();

        // Edit password logic here
        System.out.println("Password updated for " + name + ".");
    }

    private static void deletePassword() {
        System.out.print("Enter website/application name to delete password: ");
        String name = scanner.nextLine();

        // Delete password logic here
        System.out.println("Password deleted for " + name + ".");
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Delete All User Passwords\n2. Edit All User Passwords\n3. View All User Passwords\n4. Logout");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 4) break;

            switch (choice) {
                case 1:
                    admin.deleteAllPasswords(users);
                    break;
                case 2:
                    admin.editAllPasswords(users);
                    break;
                case 3:
                    admin.viewAllPasswords(users);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
