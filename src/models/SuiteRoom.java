package models;

public class SuiteRoom extends Room {
    public SuiteRoom(int roomNumber) {
        super(roomNumber, 350.0); // Suite Room price
    }

    @Override
    public void displayRoomInfo() {
        System.out.println("[Suite]    Room #" + roomNumber + 
                           " | Price: $" + price + 
                           " | Available: " + isAvailable);
    }
}
