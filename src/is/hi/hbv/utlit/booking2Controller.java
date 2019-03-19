package is.hi.hbv.utlit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.nio.file.*;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

public class booking2Controller implements Initializable {

    @FXML
    private ImageView imageID;

    @FXML
    private Button okID;

    @FXML
    private Button deleteID;
    private  String booking;
    //String img = "is/hi/hbv/utlit/img/Roomimage/boo0king/" + booking + ".png";
    //Image image = new Image("is/hi/hbv/utlit/img/Roomimage/booking/" + booking + ".png");
    public void setText12 (String BookingID, String PinID) {
        Image image = new Image("is/hi/hbv/utlit/img/Roomimage/booking/" + BookingID + ".png");
        imageID.setImage(image);
        booking = BookingID;
    }


    public void deleteImage(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Warning !");
        alert.setContentText("Are you sure you want to cancel this reversation ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deleteDir(new File( "src/is/hi/hbv/utlit/img/Roomimage/booking/" + booking + ".png"));
        }
    }
    public static boolean deleteDir(File dir) {
        return dir.delete();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
