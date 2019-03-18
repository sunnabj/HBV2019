package is.hi.hbv.vinnsla;

/*
* Klasi sem heldur utan um hótel hluti.
 */

import java.util.ArrayList;

public class Hotel {

    private int stars;
    private int minprice;
    private int reviewNr;
    private String name;
    private int hotelID;
    private String hotelInfo;
    private String hotelAddress;
    private String phoneNr;

    //Name, HotelID, Stars, ReviewID, minPrice

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

    public ArrayList<Object> getRooms() {
        HotelsDAO db = new HotelsDAO();
        return db.getRoomsInHotel(hotelID);
    }

    public ArrayList<String> getReviews() {
        HotelsDAO db = new HotelsDAO();
        return db.getHotelReviews(hotelID);
    }

    public String toString() {
        return name + " - Price from: " + minprice + " - Stars: " + stars + " - " + reviewNr + " reviews";
    }

}
