package is.hi.hbv.vinnsla;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.JDBCType.INTEGER;

public class HotelsDAO {

    private Connection conn;
    private Statement stmt;
    private ResultSet r;

    public HotelsDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            // db parameters
            String url = "jdbc:sqlite:hotels.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Hér koma get föll til að ná í hluti úr gagnagrunninum, með queries
    // eða jafnvel til að update-a eitthvað í gagnagrunninum.
    // Getum svo unnið með niðurstöðurnar einhvern veginn til að birta þær.

    /*
    * Test
     */
    public ArrayList<String> getHotelNames() {
        ArrayList<String> hotels = new ArrayList<String>();

        try {
            stmt = conn.createStatement();
            r = stmt.executeQuery("select name from Hotel");

            while (r.next()) {
                hotels.add(r.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    /*
    * Test
     */
    public ArrayList<String> getHotelRooms() {
        ArrayList<String> rooms = new ArrayList<String>();

        try {
            stmt = conn.createStatement();
            r = stmt.executeQuery("select * from Room");

            while (r.next()) {
                rooms.add(r.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    /*
    * Version 1: Fall sem leitar að hótelum eftir leitarskilyrðum - skilar strenglista með nöfnum hótela
     */
    public ArrayList<String> getHotelsSearch(int low, int high, String area, int guests) {
        ArrayList<String> hotels = new ArrayList<String>();
        try {
            stmt = conn.createStatement();
            PreparedStatement p = conn.prepareStatement("SELECT DISTINCT Hotel.Name, Room.Rate FROM Hotel, Room WHERE Rate <= ? AND Rate > ?" +
                    "AND area = ? AND GuestNumber = ? AND Hotel.HotelID = Room.HotelID");
            p.setInt(1, high);
            p.setInt(2, low);
            if (area.equals("North")) p.setString(3, "Norðurland");
            else if (area.equals("South")) p.setString(3, "Suðurland");
            else if (area.equals("West")) p.setString(3, "Vesturland");
            else if (area.equals("East")) p.setString(3, "Austurland");
            else if (area.equals("Capital area")) p.setString(3, "Höfuðborgarsvæðið");
            p.setInt(4, guests);
            r = p.executeQuery();

            while (r.next()) {
                System.out.println(r);
                hotels.add(r.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }
    /*
    * Version 2 - Aðal!!!
    * Fall sem leitar að hótelum eftir leitarskilyrðum - skilar object lista af hótel objects
    * Þeir eru samt eiginlega herbergi, innihalda verð og Room number
     */
    public ArrayList<Object> getHotelsSearchA(int high, String area, int guests) {
        ArrayList<Object> hotels = new ArrayList<Object>();

        try {
            stmt = conn.createStatement();
            //PreparedStatement p = conn.prepareStatement("SELECT Hotel.Name, Room.Rate, Hotel.Stars, Hotel.ReviewID, Room.RoomNumber" +
            //        " FROM Hotel, Room WHERE minPrice <= ?" +
            //     "AND area = ? AND minGuest <= ? AND maxGuest >= ? AND Hotel.HotelID = Room.HotelID");
             PreparedStatement p = conn.prepareStatement("SELECT Name, HotelID, Stars, ReviewID, minPrice, Address, Phone" +
                     " FROM Hotel WHERE minPrice <= ? AND Area = ? AND minGuest <= ? AND maxGuest >= ?");

            p.setInt(1, high);
            if (area.equals("North")) p.setString(2, "Norðurland");
            else if (area.equals("South")) p.setString(2, "Suðurland");
            else if (area.equals("West")) p.setString(2, "Vesturland");
            else if (area.equals("East")) p.setString(2, "Austurland");
            else if (area.equals("Capital area")) p.setString(2, "Höfuðborgarsvæðið");
            // Vantar all areas! Útfæra!
            p.setInt(3, guests);
            p.setInt(4, guests);
            r = p.executeQuery();
            // Fyrir hverja niðurstöðu í query er búið til nýtt "hótel" - því bætt á lista sem er svo skilað
            while (r.next()) {
                Hotel hotel = new Hotel(r.getString(1), r.getInt(2), r.getInt(3), r.getInt(4), r.getInt(5), r.getString(6), r.getString(7));
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public ArrayList<Object> getRoomsInHotel(int hotelID) {
        ArrayList<Object> rooms = new ArrayList<Object>();
        try {
            stmt = conn.createStatement();
            PreparedStatement p = conn.prepareStatement("SELECT RoomID, RoomNumber, Rate, GuestNumber FROM Room WHERE HotelID = ?");
            p.setInt(1, hotelID);
            r = p.executeQuery();
            while (r.next()) {
                Room room = new Room(r.getInt(4), r.getInt(3), r.getInt(1), r.getInt(2));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }


    protected void finalize() {
        try {
            if (r != null) r.close();
            if (stmt != null) stmt.close();
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        HotelsDAO hotel = new HotelsDAO();
    }
}
