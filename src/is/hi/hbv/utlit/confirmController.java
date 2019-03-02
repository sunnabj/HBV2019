package is.hi.hbv.utlit;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import is.hi.hbv.vinnsla.Hotel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class confirmController {

    @FXML
    private AnchorPane Reservation;

    @FXML
    private Label resultBooking;

    @FXML
    private Label resultInfo;

    @FXML
    private Label getName;

    @FXML
    private Button saveButton;

    public void setText12 (Hotel chosenHotel, long daycountvalue, LocalDate arrivalchoicevalue, LocalDate departurechoicevalue,
                           int guestnumbervalue) {
        getName.setText(String.valueOf(chosenHotel));
        resultBooking.setText("---------Booking Infomation---------\n\n" +
                        "Arrival : " + arrivalchoicevalue + "\n" +
                        "Departure : " + departurechoicevalue + "\n" +
                        "Number of Guest : " + guestnumbervalue + "\n" +
                        "Unit Type : " + " 1 - Kingsize - 70.000 kr \n" + "\n"
                );
    }

    public void setText123 (String Firstname,String Lastname,String Email,String Phone,String Address,String Kennitala,String Card, String List) {
        resultInfo.setText( "------------Total Cost------------\n\n" +
                            List + "\n" +
                            "---------Guest Infomation---------\n\n" +
                            "Guest Name : Mr/Mrs." + Firstname + "\n" +
                            "Last Name : " + Lastname + "\n" +
                            "Email : " + Email + " \n" +
                            "Phone nr. : " + Phone + "\n" +
                            "Address : " + Address + "\n" +
                            "Kennitala : " + Kennitala + "\n" +
                            "Card-number : " + Card + "\n"
        );
    }
    @FXML
    void nextPage(ActionEvent actionEvent) throws IOException {
        saveImage();
        Continue(actionEvent);
        //saveInfo(actionEvent);
    }

    private void saveImage(){
        Stage stage123 = (Stage) saveButton.getScene().getWindow();
        Image i = Reservation.snapshot(null, null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showSaveDialog(stage123);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(i, null), "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void saveInfo(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Save Card-infomation !!!");
        alert.setContentText("Do you want to save your\n"+
                "Card-infomation for next booking ?");
        alert.resultProperty().addListener((observable, previous, current) -> {
            if (current == ButtonType.YES) {
                // TODO . this
            } else if (current == ButtonType.NO) {
                System.exit(1);
            }
        });
        alert.show();
    }

    private void Continue(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Booking has confirmed !!!");
        alert.setContentText("Thanks your booking has confirm\n"+
                "Do you want to book another one ?");
        alert.resultProperty().addListener((observable, previous, current) -> {
            if (current == ButtonType.YES) {
                // Loadum nýrri scene -> herbergi.fxml
                Parent MorePic_parent = null;
                try {
                    MorePic_parent = FXMLLoader.load(getClass().getResource("Search.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene Search_scene = new Scene(MorePic_parent);
                Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                main_stage.setTitle("Hótel Íslands");
                main_stage.setScene(Search_scene);
                main_stage.show();
            } else if (current == ButtonType.NO) {
                System.exit(1);
            }
        });
        alert.show();
    }

}
