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
            String url = "jdbc:sqlite:hotels.db";
            // Connection to the database is created
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    * This function looks for hotels according to specific search conditions and returns a list of hotel objects.
    * The parameters are high: the maximum room price chosen, area: the region chosen, and guests: Number of guests chosen
     */
    public ArrayList<Object> HotelSearch(int high, String area, int guests) {
        ArrayList<Object> hotels = new ArrayList<Object>();

        try {
            stmt = conn.createStatement();
            PreparedStatement p = conn.prepareStatement("SELECT Name, HotelID, Stars, ReviewTotal, minPrice, Address, Phone, HotelInfo" +
                     " FROM Hotel WHERE minPrice <= ? AND Area = ? AND minGuest <= ? AND maxGuest >= ?");

            p.setInt(1, high);
            if (area.equals("North region")) p.setString(2, "Norðurland");
            else if (area.equals("South region")) p.setString(2, "Suðurland");
            else if (area.equals("West region")) p.setString(2, "Vesturland");
            else if (area.equals("East region")) p.setString(2, "Austurland");
            else if (area.equals("Capital area")) p.setString(2, "Höfuðborgarsvæðið");
            if (guests > 1) {
                p.setInt(3, guests);
                p.setInt(4, guests);
            }
            // If 1 is chosen, all hotels are chosen (that fulfill other chosen conditions).
            else {
                p.setInt(3, 10);
                p.setInt(4, 1);
            }


            r = p.executeQuery();
            // For each query result, a new hotel object is created and added to a list which is then returned.
            while (r.next()) {
                Hotel hotel = new Hotel(r.getString(1), r.getInt(2), r.getInt(3),
                        r.getInt(4), r.getInt(5), r.getString(6), r.getString(7),
                        r. getString(8));
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    /*
    * This function takes in a unique hotelID and uses an SQL query to return
    * a list of room objects matching the hotel
     */
    public ArrayList<Object> getRoomsInHotel(int hotelID) {
        ArrayList<Object> rooms = new ArrayList<Object>();
        try {
            stmt = conn.createStatement();
            PreparedStatement p = conn.prepareStatement("SELECT RoomID, RoomNumber, Rate, GuestNumber, RoomInfo FROM Room WHERE HotelID = ?");
            p.setInt(1, hotelID);
            r = p.executeQuery();
            while (r.next()) {
                Room room = new Room(r.getInt(4), r.getInt(3), r.getInt(1), r.getInt(2), r.getString(5));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    /*
    * This function takes in a unique hotelID and returns a list of reviews corresponding to that hotel
     */
    public ArrayList<String> getHotelReviews(int hotelID) {
        ArrayList<String> reviews = new ArrayList<String>();
        try {
            stmt = conn.createStatement();
            PreparedStatement p = conn.prepareStatement("SELECT Review FROM Reviews WHERE HotelID = ?");
            p.setInt(1, hotelID);
            r = p.executeQuery();
            while (r.next()) {
                reviews.add(r.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    /*
    * Database connection is closed
     */
    protected void finalize() {
        try {
            if (r != null) r.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        HotelsDAO hotel = new HotelsDAO();
    }
}
