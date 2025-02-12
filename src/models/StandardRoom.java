package models;

public class StandardRoom extends Room {
    public StandardRoom(int roomNumber) {
        super(roomNumber, 100.0); // Standard Room price
    }

    @Override
    public void displayRoomInfo() {
        System.out.println("[Standard] Room #" + roomNumber + 
                           " | Price: $" + price + 
                           " | Available: " + isAvailable);
    }
}
