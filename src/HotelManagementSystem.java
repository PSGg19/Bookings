import models.User;
import java.util.Scanner;

public class HotelManagementSystem {
    private static Scanner sc = new Scanner(System.in);
    private static Hotel hotel = new Hotel(); // Initialize the Hotel object

    public static void main(String[] args) {
        startApp();
    }

    private static void startApp() {
        while (true) {
            System.out.println("\n====== HOTEL BOOKING SYSTEM ======");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    registerFlow();
                    break;
                case "2":
                    loginFlow();
                    break;
                case "3":
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // --------------------- REGISTER ---------------------
    private static void registerFlow() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        if (hotel.isUserExists(username)) {
            System.out.println("Username already exists. Try another one.");
            return;
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.print("Are you an admin? (yes/no): ");
        boolean isAdmin = sc.nextLine().trim().equalsIgnoreCase("yes");

        hotel.registerUser(username, password, isAdmin);
        System.out.println("User registered successfully!");
    }

    // --------------------- LOGIN ---------------------
    private static void loginFlow() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User loggedIn = hotel.login(username, password);
        if (loggedIn == null) {
            System.out.println("Invalid credentials. Login failed!");
            return;
        }

        if (loggedIn.isAdmin()) {
            adminMenu(loggedIn);
        } else {
            userMenu(loggedIn);
        }
    }

    // --------------------- USER MENU ---------------------
    private static void userMenu(User user) {
        while (true) {
            System.out.println("\n===== USER MENU =====");
            System.out.println("Logged in as: " + user.getUsername());
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel a Booking");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    hotel.displayAvailableRooms();
                    break;
                case "2":
                    System.out.print("Enter room number: ");
                    int rNo = parseIntSafe(sc.nextLine());
                    System.out.print("Enter number of nights: ");
                    int nights = parseIntSafe(sc.nextLine());
                    if (rNo <= 0 || nights <= 0) {
                        System.out.println("Invalid input. Try again.");
                        break;
                    }
                    hotel.bookRoom(user.getUsername(), rNo, nights);
                    break;
                case "3":
                    hotel.displayUserBookings(user.getUsername());
                    break;
                case "4":
                    System.out.print("Enter booking ID to cancel: ");
                    int bID = parseIntSafe(sc.nextLine());
                    hotel.cancelBooking(user.getUsername(), bID);
                    break;
                case "5":
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // --------------------- ADMIN MENU ---------------------
    private static void adminMenu(User user) {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("Logged in as: " + user.getUsername());
            System.out.println("1. View All Bookings");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Add New Room");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    hotel.displayAllBookings();
                    break;
                case "2":
                    hotel.displayAvailableRooms();
                    break;
                case "3":
                    System.out.print("Enter new room number: ");
                    int rNo = parseIntSafe(sc.nextLine());
                    System.out.print("Enter room type (Standard/Deluxe/Suite): ");
                    String type = sc.nextLine();
                    System.out.print("Enter price: ");
                    double price = parseDoubleSafe(sc.nextLine());
                    if (rNo <= 0 || price <= 0) {
                        System.out.println("Invalid input. Try again.");
                        break;
                    }
                    hotel.addNewRoom(rNo, type, price);
                    break;
                case "4":
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // --------------------- HELPER METHODS ---------------------
    private static int parseIntSafe(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double parseDoubleSafe(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
