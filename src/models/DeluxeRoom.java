package models;

public class DeluxeRoom extends Room {
    public DeluxeRoom(int roomNumber) {
        super(roomNumber, 200.0); // Deluxe Room price
    }

    @Override
    public void displayRoomInfo() {
        System.out.println("[Deluxe]   Room #" + roomNumber + 
                           " | Price: $" + price + 
                           " | Available: " + isAvailable);
    }
}
