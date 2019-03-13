package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.Hotel;
import is.hi.hbv.vinnsla.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class servicesController {

    @FXML
    private CheckBox swimming;      // Swim tjékk box

    @FXML
    private CheckBox spa;           // Spa tjékk box

    @FXML
    private Label totalslist;       // Heildalist label birta

    @FXML
    private CheckBox pickup;        // Pickup tjékk box

    @FXML
    private Label totalscost;       // Heildaverð label birta

    @FXML
    private CheckBox breakfast;     // Breakfast tjékk box

    @FXML
    private CheckBox dinner;        // Dinner tjékk box

    @FXML
    private CheckBox rental;        // Rental car tjékk box

    @FXML
    private Button nextButton;

    private String a;       // String fyrir list
    private String b;       // String fyrir heildakosta

    private Hotel chosenHotel;
    private long daycountvalue;
    private LocalDate arrivalchoicevalue;
    private LocalDate departurechoicevalue;
    private int guestnumbervalue;

    //
    //
    @FXML
    void nextPage(ActionEvent actionEvent) {
        Stage stage123 = (Stage) nextButton.getScene().getWindow();
        stage123.close();
        String list_text = b;
        String cost_text = a;

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("payment.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PaymentController display = Loader.getController();
        display.setText12(list_text,cost_text);
        // display.setChosenHotel(chosenHotel);
        display.setValues(chosenHotel, daycountvalue, arrivalchoicevalue, departurechoicevalue, guestnumbervalue);

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
    }

    // Setfall fyrir Count fall
    public void setCount(String s)
    {
        a= s;
    }
    // Getfall fyrir Count
    public String getCount()
    {
        return a;
    }
    // Setfall fyrir Choice
    public void setChoice(String s)
    {
        b= s;
    }
    // Getfall fyrir choice
    public String getChoice()
    {
        return b;
    }

    // Þetta checkbox leyfa notenda bæta við þjónustu sem þeir vilt
    // virka einfalt eins og er xD
    void checkbox(){
        int count = 0;
        String choice = "";
        if (pickup.isSelected()) {
            count+=2500;
            choice += pickup.getText()+ " 2500 kr\n";
        }

        if (breakfast.isSelected()) {
            count+=1980;
            choice += breakfast.getText()+ " 1980 kr\n";
        }

        if (dinner.isSelected()) {
            count+=1780;
            choice += dinner.getText()+ " 1780 kr\n";
        }

        if (rental.isSelected()) {
            count+=4500;
            choice += rental.getText()+ " 4500 kr\n";
        }

        if (spa.isSelected()) {
            count+=7500;
            choice += spa.getText()+ " 7500 kr\n";
        }

        if (swimming.isSelected()) {
            count+=998;
            choice += swimming.getText()+ " 998 kr\n";
        }
        totalscost.setText("Totals cost : " + count +" kr.");
        totalslist.setText(choice);
        setCount(String.valueOf(count));
        setChoice(choice);
    }

    public void setHotel(Hotel hotel) {
        chosenHotel = hotel;
    }

    public void setValues(Hotel hotel, long daycount, LocalDate arrival, LocalDate departure, int guests) {
        chosenHotel = hotel;
        daycountvalue = daycount;
        arrivalchoicevalue = arrival;
        departurechoicevalue = departure;
        guestnumbervalue = guests;
    }

    // Kalla á aðferð checkbox
    //
    @FXML
    void handleButtonAction() {
        checkbox();
    }
}


