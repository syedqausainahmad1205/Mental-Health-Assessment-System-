import java.util.Scanner;
import java.util.List;

public class Login {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== WELCOME =====");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine());

                    if (choice >= 1 && choice <= 3) {
                        break;
                    } else {
                        System.out.print("Enter valid choice (1-3): ");
                    }

                } catch (Exception e) {
                    System.out.print("Invalid input. Enter number (1-3): ");
                }
            }

            switch (choice) {

                case 1:
                    System.out.println("You can now login with your credentials.");
                    loginFlow(sc);
                    break;

                case 2:
                    registerFlow(sc);
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);
    }

    public static void loginFlow(Scanner sc) {

        String email;
        String password;

        while (true) {
            System.out.print("Enter Email: ");
            email = sc.nextLine().trim();

            if (!email.isEmpty()) break;
            else System.out.println("Email cannot be empty!");
        }

        while (true) {
            System.out.print("Enter Password: ");
            password = sc.nextLine().trim();

            if (!password.isEmpty()) break;
            else System.out.println("Password cannot be empty!");
        }

        int userId = UserService.loginUser(email, password);

        if (userId != -1) {
            System.out.println("Login successful!");
            showMenu(userId, sc);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    public static void registerFlow(Scanner sc) {

        String name, email, password;

        while (true) {
            System.out.print("Enter Name: ");
            name = sc.nextLine().trim();
            if (!name.isEmpty()) break;
            else System.out.println("Name cannot be empty!");
        }

        while (true) {
            System.out.print("Enter Email: ");
            email = sc.nextLine().trim();
            if (!email.isEmpty()) break;
            else System.out.println("Email cannot be empty!");
        }

        while (true) {
            System.out.print("Enter Password: ");
            password = sc.nextLine().trim();
            if (password.length() >= 4) break;
            else System.out.println("Password must be at least 4 characters!");
        }

        boolean success = UserService.registerUser(name, email, password);

        if (success) {
            System.out.println("User registered successfully!");
            System.out.println("You can now login with your credentials.");
        } else {
            System.out.println("Registration failed. Email may already exist.");
        }
    }

    public static void showMenu(int userId, Scanner sc) {

        int choice;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Take Assessment");
            System.out.println("2. View History");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");

            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine());

                    if (choice >= 1 && choice <= 3) {
                        break;
                    } else {
                        System.out.print("Enter valid choice (1-3): ");
                    }

                } catch (Exception e) {
                    System.out.print("Invalid input. Enter number (1-3): ");
                }
            }

            switch (choice) {
                case 1:
                    AssessmentResult result = Assessment.startAssessment(userId);
                    break;

                case 2:
                    List<History> history = HistoryService.getHistory(userId);

                    if (history.isEmpty()) {
                        System.out.println("No assessment history found.");
                    } else {
                        System.out.println("\n===== YOUR HISTORY =====");

                        for (History h : history) {
                            System.out.println("Date: " + h.getDate());
                            System.out.println("Score: " + h.getScore());
                            System.out.println("Category: " + h.getCategory());
                            System.out.println("---------------------------");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Logged out successfully!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);
    }
}