package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.Hotel;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class confirmController {

    @FXML
    private Label result;

    @FXML
    private Label getName;

    public void setText12 (Hotel chosenHotel, long daycountvalue, LocalDate arrivalchoicevalue, LocalDate departurechoicevalue,
                           int guestnumbervalue, String totalslist, String count) {
        int room = 70000;
        int i = Integer.parseInt(count);
        int x = room+i;
        getName.setText(String.valueOf(chosenHotel));
        result.setText("Arrival : " + arrivalchoicevalue + "\n" +
                        "Departure : " + departurechoicevalue + "\n" +
                        "Number of Guest : " + guestnumbervalue + "\n" +
                        "Unit Type : " + " 1 - Kingsize - 70.000 kr \n" + "\n" +
                        totalslist + "\n" +
                        "Total Charges : " + count);
    }
}
