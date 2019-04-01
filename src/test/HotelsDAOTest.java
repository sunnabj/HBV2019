package test;

import is.hi.hbv.vinnsla.Hotel;
import is.hi.hbv.vinnsla.HotelsDAO;
import is.hi.hbv.vinnsla.Room;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class HotelsDAOTest {
    private int maxprice;
    private String area;
    private int guestnumber;
    private int hotelID;
    private HotelsDAO db;

    /**
     * The first three conditions where chosen with the aim of returning all the hotels in the northern region
     * in the hotelSearch function.
     * hotelID was chosen randomly for the other two functions.
     * An instance of the HotelsDAO class was initialized here since it's the class we are testing.
     */
    @Before
    public void setUp() {
        maxprice = 45000;
        area = "North region";
        guestnumber = 2;
        hotelID = 12345;
        db = new HotelsDAO();
    }

    /**
     * None of the functions change the original conditions, so implementation of tearDown is not needed.
     */
    @After
    public void tearDown() {
    }

    /**
     * According to our search conditions, we expect our function to return an
     * ArrayList of 3 hotels from the northern region of Iceland.
     */
    @Test
    public void hotelSearch() {
        ArrayList<Hotel> expectedHotels = new ArrayList<>();
        Hotel hotelA = new Hotel("Hótel Akureyri Dynheimar", 77766, 4,
                2, 19000, "Hafnarstræti 73, 600 Akureyri", "666-4444",
                "Like Viking sagas? Then our hotel is the only hotel for you. Come experience the life of vikings at Hotel Akureyri Dynheimar, like it was in the middle ages. We provide bows and arrows so you can hunt for your own food and bonfires where you can cook. Intrigued? You should be.");
        expectedHotels.add(hotelA);
        Hotel hotelB = new Hotel("Hótel Edda Akureyri", 77788, 3,
                3, 12000, "Þórunnarstræti, 600 Akureyri", "666-7777",
                "Edda Björgvinsdóttir is one of Iceland's most beloved actresses. That is why we have devoted our hotel to her. At our hotel we have a small museum with all of Eddas biggest achievements. But you know this of course, who doesn't love Edda?");
        expectedHotels.add(hotelB);
        Hotel hotelC = new Hotel("Sigló Hótel", 88877, 4,
                4, 41000, "Snorragötu 3, 580 Siglufjörður", "777-6666",
                "Siglufjörður is whout a doubt the pearl of Iceland. With our beautiful landscape and the best golf court in all the land, this is a stop you cannot miss. Bring your clubs, play golf and enjoy life.");
        expectedHotels.add(hotelC);

        // Test
        ArrayList<Hotel> actualHotels = db.HotelSearch(maxprice, area, guestnumber);

        // Assertions
        assertEquals(expectedHotels.size(), actualHotels.size());
        assertEquals(expectedHotels, actualHotels);

    }

    /**
     * This function should return the rooms belonging to the hotel with hotelID 12345,
     * in this case: Icelandair Hotel Reykjavik Natura.
     */
    @Test
    public void getRoomsInHotel() {
        // Setup, expected data
        ArrayList<Room> expectedRooms = new ArrayList<>();
        Room roomA = new Room(4, 25000, 10001,
                1, "Hagnýtar upplýsingar - Skrifborð, sími, og straujárn og strauborð (ef um það er beðið); ferðarúm/aukarúm (aukagjald) og vagga/ungbarnarúm fylgir eru í boði eftir beiðni.");
        expectedRooms.add(roomA);
        Room roomB = new Room(4, 20000, 10002,
                2, "Hagnýtar upplýsingar - Skrifborð, sími, og straujárn og strauborð (ef um það er beðið); ferðarúm/aukarúm (aukagjald) og vagga/ungbarnarúm fylgir eru í boði eftir beiðni.");
        expectedRooms.add(roomB);
        Room roomC = new Room(2, 20000, 10003,
                3, "Hagnýtar upplýsingar - Skrifborð, sími, og straujárn og strauborð (ef um það er beðið); ferðarúm/aukarúm (aukagjald) og vagga/ungbarnarúm fylgir eru í boði eftir beiðni.");
        expectedRooms.add(roomC);
        Room roomD = new Room(2, 18500, 10004,
                4, "Hagnýtar upplýsingar - Skrifborð, sími, og straujárn og strauborð (ef um það er beðið); ferðarúm/aukarúm (aukagjald) og vagga/ungbarnarúm fylgir eru í boði eftir beiðni.");
        expectedRooms.add(roomD);

        // Test
        ArrayList<Room> actualRooms = db.getRoomsInHotel(hotelID);

        // Assertions
        assertEquals(expectedRooms.size(), actualRooms.size());
        assertEquals(expectedRooms, actualRooms);
    }

    /**
     * This function should return user reviews of the hotel with hotelID 12345,
     * in this case: Icelandair Hotel Reykjavik Natura.
     */
    @Test
    public void getHotelReviews() {

        ArrayList<String> expectedReviews = new ArrayList<>();
        expectedReviews.add("Herbergið orðið lúið og rúmið vont");
        expectedReviews.add("Mjög fínt hótel á góðum stað, stutt frá miðbæ Reykjavíkur. Snyrtilegt hótel, skemmtilega innréttað og notalegt herbergi. Vingjarnlegt starfsfólk og morgunmatur virkilega góður. Mælum hiklaust með þessu hóteli og takk fyrir okkur :)");
        expectedReviews.add("Þjónusta og starfsfólk til fyrirmyndar. Morgunmaturinn frábær og gott úrval.");
        expectedReviews.add("Great location and comfortable beds but very small room and old.");

        ArrayList<String> actualReviews = db.getHotelReviews(hotelID);

        assertEquals(expectedReviews.size(), actualReviews.size());
        assertEquals(expectedReviews, actualReviews);
    }
}