import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    Scanner scanner = new Scanner(System.in);
    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public void registerUser() {
        String newUsername;
        while (true) {
            System.out.print("Enter username: ");
            newUsername = scanner.nextLine().trim();

            if (isDuplicateUsername(newUsername)) {
                System.out.println("Username already exists. Please choose a different username.\n");
            } else {
                break;
            }
        }

        String newPassword;
        while (true) {
            System.out.print("Enter password: ");
            newPassword = scanner.nextLine().trim();

            if (isStrongPassword(newPassword)) {
                break;
            } else {
                System.out.println("\nPassword must be at least 8 characters long and include:");
                System.out.println("- At least 1 uppercase letter");
                System.out.println("- At least 1 lowercase letter");
                System.out.println("- At least 1 number");
                System.out.println("- At least 1 special character (e.g., @#$%^&*)\n");
            }
        }

        User newUser = new User(newUsername, newPassword);
        users.add(newUser);
        System.out.println("User registered successfully.\n");
    }

    public User loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
    
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
    
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(username) && user.authenticate(password)) {
                System.out.println("Login successful. Welcome, " + user.getName() + "!\n");
                return user;
            }
        }
    
        System.out.println("Invalid username or password.\n");
        return null;
    }
    
    private boolean isDuplicateUsername(String username) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isStrongPassword(String password) {
        if (password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public void editUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(user.getName())) {
                users.set(i, user);
                return;
            }
        }
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    public List<User> getAllUsers() {
        return users;
    }
}
