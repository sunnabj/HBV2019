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

    @Before
    public void setUp() throws Exception {
        maxprice = 45000;
        area = "North region";
        guestnumber = 2;
        hotelID = 12345;
        db = new HotelsDAO();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void hotelSearch() {
        ArrayList<Hotel> expectedHotels = new ArrayList<>();
        Hotel hotelA = new Hotel("Hótel Akureyri Dynheimar", 77766, 3,
                2, 19000, "Hafnarstræti 73, 600 Akureyri", "666-4444",
                "Like Viking sagas? Then our hotel is the only hotel for you. Come experience the life of vikings at Hotel Akureyri Dynheimar, like it was in the middle ages. We provide bows and arrows so you can hunt for your own food and bonfires where you can cook. Intrigued? You should be.");
        expectedHotels.add(hotelA);
        Hotel hotelB = new Hotel("Hótel Edda Akureyri", 77788, 3,
                3, 12000, "Þórunnarstræti, 600 Akureyri", "666-7777",
                "Edda Björgvinsdóttir is one of Iceland's most beloved actresses. That is why we have devoted our hotel to her. At our hotel we have a small museum with all of Eddas biggest achievements. But you know this of course, who doesn't love Edda?");
        expectedHotels.add(hotelB);
        Hotel hotelC = new Hotel("Sigló Hótel", 88877, 4,
                4, 41000, "Snorragötu 3, 580 Siglufjörður", "777-6666",
                " Siglufjörður is whout a doubt the pearl of Iceland. With our beautiful landscape and the best golf court in all the land, this is a stop you cannot miss. Bring your clubs, play golf and enjoy life.");
        expectedHotels.add(hotelC);

        // Test
        ArrayList<Hotel> actualHotels = db.HotelSearch(maxprice, area, guestnumber);

        // Assertions
        assertEquals(expectedHotels.size(), actualHotels.size());
        assertEquals(expectedHotels, actualHotels);

    }

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

    @Test
    public void getHotelReviews() {
    }
}