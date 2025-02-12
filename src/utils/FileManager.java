package utils;

import models.Booking;
import models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
    private static final String USERS_FILE = "data/users.txt";
    private static final String BOOKINGS_FILE = "data/bookings.txt";

    // ------------------------- USERS -------------------------
    public static ArrayList<User> loadUsersFromFile() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);

        try {
            if (!file.exists()) {
                // Create file and add a default admin if it doesn't exist
                file.createNewFile();
                PrintWriter pw = new PrintWriter(new FileWriter(file, true));
                // Default admin: admin / adminpass
                pw.println("admin,adminpass,true");
                pw.close();
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String username = parts[0];
                        String password = parts[1];
                        boolean isAdmin = Boolean.parseBoolean(parts[2]);
                        users.add(new User(username, password, isAdmin));
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
        return users;
    }

    public static void saveUsersToFile(ArrayList<User> users) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE, false));
            for (User user : users) {
                pw.println(user.toFileString());
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error writing to users file: " + e.getMessage());
        }
    }

    // ------------------------- BOOKINGS -------------------------
    public static ArrayList<Booking> loadBookingsFromFile() {
        ArrayList<Booking> bookings = new ArrayList<>();
        File file = new File(BOOKINGS_FILE);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            Scanner scanner = new Scanner(file);
            int maxBookingID = 999; // track highest ID found in file

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    // Format: bookingID,username,roomNumber,nights,totalAmount
                    if (parts.length == 5) {
                        int bookingID = Integer.parseInt(parts[0]);
                        String username = parts[1];
                        int roomNumber = Integer.parseInt(parts[2]);
                        int nights = Integer.parseInt(parts[3]);
                        double totalAmount = Double.parseDouble(parts[4]);

                        // Recreate a Booking object
                        Booking b = new Booking(username, roomNumber, nights, totalAmount / nights);

                        // We do a reflection trick to set the bookingID from file
                        try {
                            java.lang.reflect.Field f = b.getClass().getDeclaredField("bookingID");
                            f.setAccessible(true);
                            f.setInt(b, bookingID);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        bookings.add(b);

                        // Keep track of the max booking ID
                        if (bookingID > maxBookingID) {
                            maxBookingID = bookingID;
                        }
                    }
                }
            }
            scanner.close();

            // Next new booking starts from maxBookingID + 1
            Booking.setCounterFromFile(maxBookingID + 1);

        } catch (IOException e) {
            System.out.println("Error reading bookings file: " + e.getMessage());
        }
        return bookings;
    }

    public static void saveBookingsToFile(ArrayList<Booking> bookings) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(BOOKINGS_FILE, false));
            for (Booking b : bookings) {
                pw.println(b.toFileString());
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error writing to bookings file: " + e.getMessage());
        }
    }
}
