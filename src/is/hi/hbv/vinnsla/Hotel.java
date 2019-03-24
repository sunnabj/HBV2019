package is.hi.hbv.vinnsla;

/*
* Klasi sem heldur utan um hótel hluti.
 */

import java.util.ArrayList;

public class Hotel {

    private int stars;              // Number of stars of the hotel
    private int minprice;           // The price of the cheapest room in the hotel
    private int reviewNr;           // The number of reviews of the hotel
    private String name;            // The name of the hotel
    private int hotelID;            // A unique ID
    private String hotelInfo;       // Information about the hotel
    private String hotelAddress;    // The address of the hotel
    private String phoneNr;         // The hotel's phone number

    /*
    * The constructor for hotel objects
     */
    public Hotel(String namevalue, int hotelIdent, int starvalue, int reviews, int pricevalue, String address, String phone, String info) {
        name = namevalue;
        hotelID = hotelIdent;
        stars = starvalue;
        minprice = pricevalue;
        reviewNr = reviews;
        hotelInfo = info;
        hotelAddress = address;
        phoneNr = phone;
    }
    //Here come getters for all the instance variables
    public int getStars() {
        return stars;
    }
    public int getPrice() {
        return minprice;
    }
    public int getReviewNr() {

        return reviewNr;
    }
    public int getHotelID() {
        return hotelID;
    }
    public String getHotelInfo() {
        return hotelInfo;
    }
    public String getHotelAddress() {
        return hotelAddress;
    }
    public String getPhoneNr() {
        return phoneNr;
    }
    public String getName() {
        return name;
    }
    // Connects to the database and returns an object list of the rooms in this particular hotel
    public ArrayList<Object> getRooms() {
        HotelsDAO db = new HotelsDAO();
        return db.getRoomsInHotel(hotelID);
    }
    // Connects to the database and returns a list of the reviews of this particular hotel
    public ArrayList<String> getReviews() {
        HotelsDAO db = new HotelsDAO();
        return db.getHotelReviews(hotelID);
    }

    public String toString() {
        return name + " - Price from: " + minprice + " - Stars: " + stars + " - " + reviewNr + " reviews";
    }

}
