package test;

import is.hi.hbv.vinnsla.Hotel;
import is.hi.hbv.vinnsla.searchActivity;
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
    private ArrayList<Object> sortingTest = new ArrayList<Object>();

    /**
     * The first three conditions aim to return one single hotel from the search function. That hotel corresponds
     * to the hotel object which is initialized here as hotelA.
     * hotelB, hotelC and hotelD are hotel objects that are used to test the sorting functions since each of them
     * have different values of price, stars and number of reviews. These three are then added to a list that can
     * be used directly in the sorting functions.
     */
    @Before
    public void setUp() {
        areachoice = "North region";
        price = 25000;
        guestnumber = 3;
        hotelA = new Hotel("Hótel Edda Akureyri", 77788, 3,
                3, 12000, "Þórunnarstræti, 600 Akureyri", "666-7777",
                "Edda Björgvinsdóttir is one of Iceland's most beloved actresses. That is why we have devoted our hotel to her. At our hotel we have a small museum with all of Eddas biggest achievements. But you know this of course, who doesn't love Edda?");
        hotelB = new Hotel("Atlantic Apartments and Rooms Íbúðarhótelið", 44455, 2,
                3, 17000, "Grensásvegi 14, 108 Reykjavík", "444-2222",
                "Have you seen photos of our hotel? Do you like what you see? Book your stay with us and we'll make sure that your time in Iceland will be amazing. Nuff said.");
        hotelC = new Hotel("City Park Hotel", 11133, 3,
                5, 23000, "Hallarmúla 1, 108 Reykjavík", "333-2222",
                "Looking for amazing adventures in Iceland? The adventure begins in City Park Hotel. We offer thrilling rooftop show for you eyes where you can enjoy watching Icelands mysterious northern lights");
        hotelD = new Hotel("Icelandair hótel Reykjavík Natura", 12345, 4,
                4, 18500, "Nauthólsvegi 52, 101 Reykjavík", "123-4567",
                "One of Icelandair's most beautiful hotels. Located in central Reykjavík, close to the city's many adventures. Our hotel has much to offer and we specially recommend trying our amazing restaurant Rignir");
        sortingTest.add(hotelB);
        sortingTest.add(hotelC);
        sortingTest.add(hotelD);
    }

    /**
     * None of the functions change the original conditions, so implementation of tearDown is not needed.
     */
    @After
    public void tearDown() {
    }

    /**
     * This function should return a list containing only one hotel, that is hotelA, according to the search
     * conditions specified in the setup.
     */
    @Test
    public void hotelSearch() {
        ArrayList<Hotel> expectedHotels = new ArrayList<>();
        expectedHotels.add(hotelA);
        ArrayList<Hotel> actualHotels = searchActivity.hotelSearch(areachoice, guestnumber, price);

        assertEquals(expectedHotels.size(), actualHotels.size());
        assertEquals(expectedHotels, actualHotels);
    }

    /**
     * This function should return a list of hotels sorted in ascending order depending on minimum room price.
     */
    @Test
    public void priceSort() {
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(hotelB);
        expected.add(hotelD);
        expected.add(hotelC);
        ArrayList<Object> actual = searchActivity.priceSort(FXCollections.observableArrayList(sortingTest));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    /**
     * This function should return a list of hotels sorted in ascending order depending on number of reviews.
     */
    @Test
    public void reviewSort() {
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(hotelB);
        expected.add(hotelD);
        expected.add(hotelC);
        ArrayList<Object> actual = searchActivity.reviewSort(FXCollections.observableArrayList(sortingTest));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    /**
     * This function should return a list of hotels sorted in ascending order depending on number of stars.
     */
    @Test
    public void starSort() {
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(hotelB);
        expected.add(hotelC);
        expected.add(hotelD);
        ArrayList<Object> actual = searchActivity.starSort(FXCollections.observableArrayList(sortingTest));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }
}