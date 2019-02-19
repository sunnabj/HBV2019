package is.hi.hbv.vinnsla;

/*
* Klasi sem heldur utan um hótel hluti. Þetta er samt eiginlega herbergi eins og er.
 */

public class Hotel {

    private int stars;
    private int price;
    private int reviewID;
    private String name;
    private int roomNr;

    public Hotel(String namevalue, int pricevalue, int starvalue, int reviews, int room) {
        name = namevalue;
        stars = starvalue;
        price = pricevalue;
        reviewID = reviews;
        roomNr = room;
    }

    public int getStars() {
        return stars;
    }
    public int getPrice() {
        return price;
    }
    public int getReviewID() {
        return reviewID;
    }

    public String toString() {
        return name + " - room number " + roomNr + " - Price: " + price;
    }
/*
    public void setStars(int starvalue) {
        stars = starvalue;
    }
    public void setPrice(int pricevalue) {
        price = pricevalue;
    }
    public void setReviewID(int reviews) {
        reviewID = reviews;
    }
*/
}
