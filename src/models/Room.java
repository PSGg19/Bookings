package models;

public abstract class Room {
    protected int roomNumber;
    protected double price;
    protected boolean isAvailable;

    public Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookRoom() {
        this.isAvailable = false;
    }

    public void cancelBooking() {
        this.isAvailable = true;
    }

    public abstract void displayRoomInfo();
}
