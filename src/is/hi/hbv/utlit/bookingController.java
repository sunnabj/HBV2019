package is.hi.hbv.utlit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        if (isValidCredentials())
        {
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setHeaderText("Tip");
        alert.setContentText("For better user experiment\n"+
                            "Please restart this application before " +
                            "check your booking");
        alert.showAndWait() ;
        isValidCredentials();
        bookingID.setTooltip(new Tooltip("Please enter your Booking ID from last payment"));
        pinID.setTooltip(new Tooltip("Please enter your Pin ID from last payment"));
        confirmID.setTooltip(new Tooltip("Go to booking manage"));
    }
}
