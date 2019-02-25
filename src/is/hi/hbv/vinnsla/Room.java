package is.hi.hbv.vinnsla;

public class Room {
    private int guestNumber;
    private int price;
    private int roomID;
    private int roomNr;

    public Room(int guests, int pricevalue, int roomIdent, int roomnumber) {
        guestNumber = guests;
        price = pricevalue;
        roomID = roomIdent;
        roomNr = roomnumber;
    }

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

    public String toString() {
        return "Room number " + roomNr;
    }
}
