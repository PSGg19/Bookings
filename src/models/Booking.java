package models;

public class Booking {
    private static int bookingCounter = 1000;
    private int bookingID;
    private String username;
    private int roomNumber;
    private int nights;
    private double totalAmount;

    public Booking(String username, int roomNumber, int nights, double pricePerNight) {
        this.bookingID = bookingCounter++;
        this.username = username;
        this.roomNumber = roomNumber;
        this.nights = nights;
        this.totalAmount = nights * pricePerNight;
    }

    public int getBookingID() {
        return bookingID;
    }

    public String getUsername() {
        return username;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public static void setCounterFromFile(int val) {
        bookingCounter = val;
    }

    public void displayBookingDetails() {
        System.out.println("Booking ID: " + bookingID + 
                           " | User: " + username + 
                           " | Room #: " + roomNumber + 
                           " | Nights: " + nights + 
                           " | Total: $" + totalAmount);
    }

    // Converts booking to a CSV line for file storage
    public String toFileString() {
        return bookingID + "," + username + "," + roomNumber + "," + nights + "," + totalAmount;
    }
}
