package is.hi.hbv.utlit;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

public class searchController {
    @FXML
    public DatePicker arrivalchoice;
    @FXML
    public DatePicker departurechoice;
    @FXML
    public ComboBox areachoice;
    @FXML
    public ComboBox pricechoice;
    @FXML
    public ComboBox guestnumber;
    @FXML
    public Button searchButton;
    @FXML
    public Button mailListButton;
    @FXML
    public AnchorPane frontPageAnchor;

    ObservableList<String> areaList = FXCollections.observableArrayList("Capital area", "North", "South", "East", "West", "All areas");

    ObservableList<String> priceList = FXCollections.observableArrayList("Less than 3000 ISK", "3000-6000 ISK", "6000-10000 ISK", "10000-15000 ISK", "15000-20000 ISK", "More than 20000 ISK", "Doesn't matter");

    ObservableList<String> guestList = FXCollections.observableArrayList("1", "2", "3 or more");


    @FXML private void initialize() {
        areachoice.setValue("Choose an area...");
        areachoice.setItems(areaList);
        pricechoice.setValue("Price per night");
        pricechoice.setItems(priceList);
        guestnumber.setValue("Choose number of guests...");
        guestnumber.setItems(guestList);
    }


    public void hotelsearchHandler(ActionEvent actionEvent) {
    }

    public void mailListDialogHandler(ActionEvent actionEvent) {
    }

}
