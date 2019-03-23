package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.Hotel;
import is.hi.hbv.vinnsla.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class servicesController implements Initializable {

    @FXML
    public TextArea roomInfoBox;    // Sýnir aukalegar upplýsingar um valið herbergi
    @FXML
    public Label roomPriceLabel;    // Sýnir verð herbergis
    @FXML
    public Label guestNumberLabel;  // Sýnir gestafjölda í herbergi

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

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String kennitala;
    private String card;

    private String a;       // String fyrir list
    private String b;       // String fyrir heildakosta

    private Hotel chosenHotel;
    private Room chosenRoom;
    private long daycountvalue;
    private LocalDate arrivalchoicevalue;
    private LocalDate departurechoicevalue;
    private int guestnumbervalue;
    private int totalPrice;
    private int servicePrice;

    //
    //
    @FXML
    void nextPage(ActionEvent actionEvent) {
        Stage stage123 = (Stage) nextButton.getScene().getWindow();
        stage123.close();
        String list_text = b;
        String cost_text = a;

        if (pickup.isSelected()) {
            totalPrice += 2500;
        }
        if (breakfast.isSelected()) {
            totalPrice += 1980;
        }

        if (dinner.isSelected()) {
            totalPrice += 1780;
        }

        if (rental.isSelected()) {
            totalPrice += 4500;
        }

        if (spa.isSelected()) {
            totalPrice += 7500;
        }

        if (swimming.isSelected()) {
            totalPrice += 998;
        }

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("payment.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PaymentController display = Loader.getController();
        // display.setChosenHotel(chosenHotel);
        display.setValues(chosenHotel, chosenRoom, totalPrice, servicePrice, daycountvalue, arrivalchoicevalue, departurechoicevalue, guestnumbervalue);
        display.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
        display.setText12(list_text, cost_text);

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
    }

    // Setfall fyrir Count fall
    public void setCount(String s) {
        a = s;
    }

    // Getfall fyrir Count
    public String getCount() {
        return a;
    }

    // Setfall fyrir Choice
    public void setChoice(String s) {
        b = s;
    }

    // Getfall fyrir choice
    public String getChoice() {
        return b;
    }

    // Þetta checkbox leyfa notenda bæta við þjónustu sem þeir vilt
    // virka einfalt eins og er xD
    void checkbox() {
        int count = 0;
        String choice = "";

        if (!pickup.isSelected() && !breakfast.isSelected() && !dinner.isSelected()
                && !rental.isSelected() && !spa.isSelected() && !swimming.isSelected()) {
            // count += 0;
            choice += " No service : 0 kr\n";
            totalscost.setText("Added cost : " + count + " ISK");
            totalslist.setText(choice);
        } else {
            if (pickup.isSelected()) {
                count += 2500;
                choice += pickup.getText() + " 2500 ISK\n";
            }
            if (breakfast.isSelected()) {
                count += 1980;
                choice += breakfast.getText() + " 1980 ISK\n";
            }

            if (dinner.isSelected()) {
                count += 1780;
                choice += dinner.getText() + " 1780 ISK\n";
            }

            if (rental.isSelected()) {
                count += 4500;
                choice += rental.getText() + " 4500 ISK\n";
            }

            if (spa.isSelected()) {
                count += 7500;
                choice += spa.getText() + " 7500 ISK\n";
            }

            if (swimming.isSelected()) {
                count += 998;
                choice += swimming.getText() + " 998 ISK\n";
            }
        }
        totalscost.setText("Added cost : " + count + " ISK");
        totalslist.setText(choice);
        setCount(String.valueOf(count));
        setChoice(choice);
        System.out.println("Count - kostnaður í services: " + count);
        servicePrice = count;
        //totalPrice += count;
        //System.out.println("Heildarkostnaður: " + totalPrice);
    }

    public void setHotel(Hotel hotel) {
        chosenHotel = hotel;
    }

    public void setValues(Hotel hotel, Room room, long daycount, LocalDate arrival, LocalDate departure, int guests) {
        chosenHotel = hotel;
        daycountvalue = daycount;
        arrivalchoicevalue = arrival;
        departurechoicevalue = departure;
        guestnumbervalue = guests;
        chosenRoom = room;
        long roomPrice = room.getPrice()*daycountvalue;
        totalPrice += roomPrice;
        roomInfoBox.setText(chosenRoom.getRoomInfo());
        roomPriceLabel.setText(String.valueOf(roomPrice) + " ISK for " + daycountvalue + " night(s)");
        guestNumberLabel.setText(String.valueOf(guests) + " guests");
    }

    // Kalla á aðferð checkbox
    //
    @FXML
    void handleButtonAction(ActionEvent actionEvent) {
        checkbox();
    }

    public void setSaveInfo(String Firstname, String Lastname, String Email, String Phone, String Address, String Kennitala, String Card) {
        firstname = Firstname;
        lastname = Lastname;
        email = Email;
        phone = Phone;
        address = Address;
        kennitala = Kennitala;
        card = Card;
        System.out.println(firstname + lastname + "services");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalscost.setText("Additional cost : " + 0 + " ISK");
        totalslist.setText("No service : 0 ISK");
        setCount(String.valueOf(0));
        setChoice("No service : 0 ISK");
        pickup.setTooltip(new Tooltip("Pickup straight from airport"));
        breakfast.setTooltip(new Tooltip("Eat break in hotel"));
        dinner.setTooltip(new Tooltip("Eat dinner in hotel"));
        rental.setTooltip(new Tooltip("Rental a car"));
        spa.setTooltip(new Tooltip("Try our spa service"));
        swimming.setTooltip(new Tooltip("Try our swimming service"));
        nextButton.setTooltip(new Tooltip("Go to payment"));
    }
}


