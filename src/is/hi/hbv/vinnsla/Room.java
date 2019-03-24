package is.hi.hbv.vinnsla;

public class Room {
    private int guestNumber;    // Number of guests in the room
    private int price;          // Price of one night in the room
    private int roomID;         // A unique ID for this room
    private int roomNr;         // The number of the room
    private String roomInfo;    // Further information about the room

    /*
    * The constructor initializes all the instance variables
     */
    public Room(int guests, int pricevalue, int roomIdent, int roomnumber, String info) {
        guestNumber = guests;
        price = pricevalue;
        roomID = roomIdent;
        roomNr = roomnumber;
        roomInfo = info;
    }

    // Get functions for all the instance variables
    public int getGuests() {
        return guestNumber;
    }
    public int getPrice() {
        return price;
    }
    public int getRoomID() {
        return roomID;
    }
    public int getRoomNr() {
        return roomNr;
    }
    public String getRoomInfo() {
        return roomInfo;
    }

    public String toString() {
        return "Room " + roomNr + " - " + price + " ISK per night - " + guestNumber + " guests";
    }
}
