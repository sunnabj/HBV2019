package is.hi.hbv.vinnsla;

/*
* Klasi sem heldur utan um hótel hluti.
 */

import java.util.ArrayList;

public class Hotel {

    private int stars;
    private int minprice;
    private int reviewID;
    private String name;
    private int hotelID;
    private String hotelInfo; // Eftir að bæta við í gagnagrunninn
    private String hotelAddress;
    private String phoneNr;

    //Name, HotelID, Stars, ReviewID, minPrice

    public Hotel(String namevalue, int hotelIdent, int starvalue, int reviews, int pricevalue, String address, String phone) {
        name = namevalue;
        hotelID = hotelIdent;
        stars = starvalue;
        minprice = pricevalue;
        reviewID = reviews;
        // hotelInfo = info; // Mun bætast við. Líka sem inntak.
        hotelAddress = address;
        phoneNr = phone;
    }

    public int getStars() {
        return stars;
    }
    public int getPrice() {
        return minprice;
    }
    public int getReviewID() {
        return reviewID;
    }
    public int getHotelID() {
        return hotelID;
    }
    // Ekki komið inn
    public String getHotelInfo() {
        return hotelInfo;
    }
    public String getHotelAddress() {
        return hotelAddress;
    }
    public String getPhoneNr() {
        return phoneNr;
    }

    public ArrayList<Object> getRooms() {
        HotelsDAO db = new HotelsDAO();
        return db.getRoomsInHotel(hotelID);
    }

    public String toString() {
        return name + " - Price from: " + minprice + " - Stars: " + stars;
    }

}
