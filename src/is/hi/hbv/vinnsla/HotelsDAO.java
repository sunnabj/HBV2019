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

    public ArrayList<String> getHotelsbyPrice(int low, int high) {
        ArrayList<String> hotels = new ArrayList<String>();
        // Þessu má breyta í stærri query með líka ? fyrir area og ? fyrir fjölda gesta og kannski ? fyrir date
        // Svo tekur maður bara fleiri parametra inn, einn fyrir hvert leitarskilyrði.
        // Þá er hægt að nota bara þetta fall fyrir leitina
        try {
            stmt = conn.createStatement();
            PreparedStatement p = conn.prepareStatement("SELECT DISTINCT Name FROM Hotel, Room WHERE Rate <= ? AND Rate > ?" +
                    "AND Hotel.HotelID = Room.HotelID");
            p.setInt(1, high);
            p.setInt(2, low);
            r = p.executeQuery();

            while (r.next()) {
                hotels.add(r.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
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
