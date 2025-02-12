import models.*;
import utils.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Hotel class manages:
 * - A list of Room objects
 * - A list of Booking objects
 * - A list of User objects
 * 
 * It handles login, registration, room booking, cancellation, 
 * and admin functionalities.
 */
public class Hotel {
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;
    private ArrayList<User> users;

    public Hotel() {
        // 1) Load existing users & bookings from file
        users = FileManager.loadUsersFromFile();
        bookings = FileManager.loadBookingsFromFile();

        // 2) Initialize rooms
        rooms = new ArrayList<>();
        // Standard Rooms: 1-3
        for (int i = 1; i <= 3; i++) {
            rooms.add(new StandardRoom(i));
        }
        // Deluxe Rooms: 4-6
        for (int i = 4; i <= 6; i++) {
            rooms.add(new DeluxeRoom(i));
        }
        // Suite Rooms: 7-9
        for (int i = 7; i <= 9; i++) {
            rooms.add(new SuiteRoom(i));
        }

        // Mark any booked room as unavailable
        syncRoomAvailability();
    }

    // Marks rooms as unavailable if they're already booked
    private void syncRoomAvailability() {
        for (Booking b : bookings) {
            for (Room r : rooms) {
                if (r.getRoomNumber() == b.getRoomNumber()) {
                    r.bookRoom();
                }
            }
        }
    }

    // ----------------------- USER MANAGEMENT -----------------------
    public boolean isUserExists(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public User registerUser(String username, String password, boolean isAdmin) {
        User newUser = new User(username, password, isAdmin);
        users.add(newUser);
        FileManager.saveUsersToFile(users);
        return newUser;
    }

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.checkPassword(password)) {
                return u;
            }
        }
        return null;
    }

    // ----------------------- ROOM OPERATIONS -----------------------
    public void displayAvailableRooms() {
        System.out.println("\n----- Available Rooms -----");
        boolean anyAvailable = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                room.displayRoomInfo();
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("No rooms are currently available.");
        }
    }

    public void addNewRoom(int roomNumber, String type, double price) {
        // Admin can dynamically add new rooms
        Room newRoom;
        switch (type.toLowerCase()) {
            case "standard":
                newRoom = new StandardRoom(roomNumber);
                break;
            case "deluxe":
                newRoom = new DeluxeRoom(roomNumber);
                break;
            case "suite":
                newRoom = new SuiteRoom(roomNumber);
                break;
            default:
                System.out.println("Invalid room type! (Use standard/deluxe/suite)");
                return;
        }
        // Overwrite price if needed
        try {
            java.lang.reflect.Field f = newRoom.getClass().getSuperclass().getDeclaredField("price");
            f.setAccessible(true);
            f.setDouble(newRoom, price);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rooms.add(newRoom);
        System.out.println("Room #" + roomNumber + " (" + type + ") added successfully!");
    }

    // ----------------------- BOOKING OPERATIONS -----------------------
    public void bookRoom(String username, int roomNumber, int nights) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                room.bookRoom();
                Booking newBooking = new Booking(username, roomNumber, nights, room.getPrice());
                bookings.add(newBooking);
                FileManager.saveBookingsToFile(bookings);
                System.out.println("\nBooking Successful!");
                newBooking.displayBookingDetails();
                return;
            }
        }
        System.out.println("Sorry, that room is unavailable or doesn't exist.");
    }

    public void cancelBooking(String username, int bookingID) {
        Booking toRemove = null;
        for (Booking b : bookings) {
            if (b.getBookingID() == bookingID && b.getUsername().equalsIgnoreCase(username)) {
                toRemove = b;
                break;
            }
        }
        if (toRemove == null) {
            System.out.println("No matching booking found for you.");
            return;
        }
        // Make the room available again
        for (Room r : rooms) {
            if (r.getRoomNumber() == toRemove.getRoomNumber()) {
                r.cancelBooking();
                break;
            }
        }
        bookings.remove(toRemove);
        FileManager.saveBookingsToFile(bookings);
        System.out.println("Booking #" + bookingID + " canceled successfully!");
    }

    public void displayUserBookings(String username) {
        System.out.println("\n----- Your Bookings -----");
        boolean found = false;
        for (Booking b : bookings) {
            if (b.getUsername().equalsIgnoreCase(username)) {
                b.displayBookingDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("You have no bookings.");
        }
    }

    public void displayAllBookings() {
        System.out.println("\n----- All Bookings -----");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Booking b : bookings) {
            b.displayBookingDetails();
        }
    }
}
