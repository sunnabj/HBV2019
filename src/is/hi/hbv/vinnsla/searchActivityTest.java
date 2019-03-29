package is.hi.hbv.vinnsla;

import javafx.collections.FXCollections;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class searchActivityTest {
    private String areachoice;
    private int price;
    private int guestnumber;
    private Hotel hotelA;
    private Hotel hotelB;
    private Hotel hotelC;
    private Hotel hotelD;
    private Hotel hotelE;
    private Hotel hotelF;
    private ArrayList<Object> sortingTest = new ArrayList<Object>();

    @Before
    public void setUp() throws Exception {
        areachoice = "North region";
        price = 25000;
        guestnumber = 2;
        hotelA = new Hotel("Hótel Akureyri Dynheimar", 77766, 3,
                2, 19000, "Hafnarstræti 73, 600 Akureyri", "666-4444",
                "Like Viking sagas? Then our hotel is the only hotel for you. Come experience the life of vikings at Hotel Akureyri Dynheimar, like it was in the middle ages. We provide bows and arrows so you can hunt for your own food and bonfires where you can cook. Intrigued? You should be.");
        hotelB = new Hotel("Hótel Edda Akureyri", 77788, 3,
                3, 12000, "Þórunnarstræti, 600 Akureyri", "666-7777",
                "Edda Björgvinsdóttir is one of Iceland's most beloved actresses. That is why we have devoted our hotel to her. At our hotel we have a small museum with all of Eddas biggest achievements. But you know this of course, who doesn't love Edda?");
        hotelC = new Hotel("Sigló Hótel", 88877, 4,
                4, 41000, "Snorragötu 3, 580 Siglufjörður", "777-6666",
                " Siglufjörður is whout a doubt the pearl of Iceland. With our beautiful landscape and the best golf court in all the land, this is a stop you cannot miss. Bring your clubs, play golf and enjoy life.");
        hotelD = new Hotel("Atlantic Apartments and Rooms Íbúðarhótelið", 44455, 2,
                3, 17000, "Grensásvegi 14, 108 Reykjavík", "444-2222",
                "Have you seen photos of our hotel? Do you like what you see? Book your stay with us and we'll make sure that your time in Iceland will be amazing. Nuff said.");
        hotelE = new Hotel("City Park Hotel", 11133, 3,
                5, 23000, "Hallarmúla 1, 108 Reykjavík", "333-2222",
                "Looking for amazing adventures in Iceland? The adventure begins in City Park Hotel. We offer thrilling rooftop show for you eyes where you can enjoy watching Icelands mysterious northern lights");
        hotelF = new Hotel("Icelandair hótel Reykjavík Natura", 12345, 4,
                4, 18500, "Nauthólsvegi 52, 101 Reykjavík", "123-4567",
                "One of Icelandair's most beautiful hotels. Located in central Reykjavík, close to the city's many adventures. Our hotel has much to offer and we specially recommend trying our amazing restaurant Rignir");
        sortingTest.add(hotelD);
        sortingTest.add(hotelE);
        sortingTest.add(hotelF);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void hotelSearch() {
        ArrayList<Hotel> expectedHotels = new ArrayList<>();
        expectedHotels.add(hotelA);
        expectedHotels.add(hotelB);
        //assertArrayEquals(hotels.toArray(), searchActivity.hotelSearch(areachoice, guestnumber, price).toArray());
        ArrayList<Hotel> actualHotels = searchActivity.hotelSearch(areachoice, guestnumber, price);

        assertEquals(expectedHotels.size(), actualHotels.size());
        assertEquals(expectedHotels, actualHotels);
    }


    @Test
    public void priceSort() {
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(hotelD);
        expected.add(hotelF);
        expected.add(hotelE);
        ArrayList<Object> actual = searchActivity.priceSort(FXCollections.observableArrayList(sortingTest));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void reviewSort() {
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(hotelD);
        expected.add(hotelF);
        expected.add(hotelE);
        ArrayList<Object> actual = searchActivity.reviewSort(FXCollections.observableArrayList(sortingTest));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void starSort() {
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(hotelD);
        expected.add(hotelE);
        expected.add(hotelF);
        ArrayList<Object> actual = searchActivity.starSort(FXCollections.observableArrayList(sortingTest));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }
}