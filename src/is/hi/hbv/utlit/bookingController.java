package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.HotelsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TextField;

public class bookingController implements Initializable {

    Connection connection= null;
    ResultSet resultSet = null;
    //Statement statement = null;
    PreparedStatement statement = null;
    @FXML
    private TextField pinID;

    @FXML
    private TextField bookingID;
    @FXML
    private Button confirmID;


    public boolean isValidCredentials() {
        String sql = "SELECT * FROM Booking WHERE BookingID = ? and PinID = ?";

        boolean let_in = false;
        System.out.println( "SELECT * FROM Booking WHERE BookingID= " + "'" + bookingID.getText() + "'"
                + " AND PinID= " + "'" + pinID.getText() + "'" );
        PreparedStatement pst = null;
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:hotels.db");
            c.setAutoCommit(false);

            System.out.println("Opened database successfully");
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM Booking WHERE BookingID= " + "'" + bookingID.getText() + "'"
                    + " AND PinID= " + "'" + pinID.getText() + "'");
            //rs.updateRow();
            //rs.update
            while ( rs.next() ) {
                if (rs.getString("BookingID") != null && rs.getString("PinID") != null) {
                    String  booking = rs.getString("BookingID");
                    System.out.println( "BookingID = " + booking );
                    String pin = rs.getString("PinID");
                    System.out.println("PinID = " + pin);
                    let_in = true;
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return let_in;
    }

    public void confirm(ActionEvent actionEvent) throws IOException {
        //Parent home_page_parent =  FXMLLoader.load(getClass().getResource("Intro.fxml"));
        //Scene home_page_scene = new Scene(home_page_parent);
        //Stage app_stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        if (isValidCredentials())
        {
            /*
            app_stage.hide(); //optional
            app_stage.setScene(home_page_scene);
            app_stage.show();*/
            // Aðferð að kalla gögna frá annara scene, ekki saman við aðra aðferð.
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("booking2.fxml"));
            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(booking2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            booking2Controller display = Loader.getController();
            display.setText12(bookingID.getText(),pinID.getText());
            Parent p = Loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Booking Nr. "+bookingID.getText());
            stage.setScene(new Scene(p));
            stage.show();
        }
        else
        {
            //bookingID.clear();
            //pinID.clear();
            //invalid_label.setText("Sorry, invalid credentials");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isValidCredentials();
        bookingID.setTooltip(new Tooltip("Please enter your Booking ID from last payment"));
        pinID.setTooltip(new Tooltip("PPlease enter your Pin ID from last payment"));
        confirmID.setTooltip(new Tooltip("Go to booking manage"));
    }
}
