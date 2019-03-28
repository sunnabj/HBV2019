package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.Hotel;
import is.hi.hbv.vinnsla.HotelsDAO;
import is.hi.hbv.vinnsla.searchActivity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class searchController implements Initializable {

    @FXML
    public DatePicker arrivalchoice; //Selection of arrival dates
    @FXML
    public DatePicker departurechoice; //Selection of departure dates
    @FXML
    public ComboBox areachoice; // Selection of areas
    @FXML
    public ComboBox pricechoice; // Selection of maximum price
    @FXML
    public ComboBox guestnumber; // Selection of number of guests
    @FXML
    public Button searchButton; // Button to start search
    @FXML
    public Button mailListButton; // Button to register for a maillist
    @FXML
    public AnchorPane frontPageAnchor;  // Pane that includes the whole search window
    @FXML
    public ToggleGroup sorting; // Ensures that only one sorting option can be chosen at once
    @FXML
    public AnchorPane mailList; // Window for the mailList
    @FXML
    public ListView resultList; // The list that shows the search results
    @FXML
    public Button chooseButton; // Button to choose hotel from the results
    @FXML
    public RadioButton priceRadio; // Radiobutton that sorts by price
    @FXML
    public RadioButton reviewRadio; // Radiobutton that sorts by review number
    @FXML
    public RadioButton starRadio; // Radiobutton that sorts by number of stars
    @FXML
    private MailListController mailListController; // Instance fot he postlist dialog controller
    @FXML
    public TextField mailID; // Reads email text
    @FXML
    private searchActivity search;

    Hotel chosenHotel; // Chosen hotel object in the reults

    int hotelindex = -1; //Current index in the result list

    private ObservableList<Object> hotelResults; // List for the hotel the search returned

    // Lists for different areas, prices and guest numbers that can be chosen in drop down lists
    private ObservableList<String> areaList = FXCollections.observableArrayList("Capital area", "North region", "South region", "East region", "West region");

    private ObservableList<String> priceList = FXCollections.observableArrayList("25000 ISK or less", "35000 ISK or less", "45000 ISK or less", "60000 ISK or less", "Doesn't matter");

    private ObservableList<String> guestList = FXCollections.observableArrayList("1", "2", "3", "4", "More");

    private String areachoicevalue;    // The value of the chosen area
    private int maxpricevalue = -1;         // The value for the chosen maximum room price
    private int guestnumbervalue = -1;      // The value for the chosen number of guests
    private LocalDate arrivalchoicevalue; // The value for the chosen arrival date
    private LocalDate departurechoicevalue; // The value for the chosen departure date
    private long daycountvalue;          // Value for the duration of stay - calculated from the arrival and departure dates
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String kennitala;
    private String card;
    private String Firstname;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Dropdown lists initialized
        areachoice.setValue("Choose an area...");
        areachoice.setItems(areaList);
        pricechoice.setValue("Price per night");
        pricechoice.setItems(priceList);
        guestnumber.setValue("Choose number of guests...");
        guestnumber.setItems(guestList);
        // Handlers for the dropdown lists initialized
        areaChoiceHandler();
        priceChoiceHandler();
        guestNumberHandler();

        // Chosen values in the result list observed
        MultipleSelectionModel<Object> lsm = resultList.getSelectionModel();
        lsm.selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                // The index in the result list
                hotelindex = lsm.getSelectedIndex();
            }
        });
    }

    /*
     * The chain of events happening when the search button is pushed.
     */
    public void hotelsearchHandler(ActionEvent actionEvent) throws SQLException {

        try {
            // Chosen dates are fetched and duration of stay calculated.
            // If nothing is chosen, nullpointerexception arises, and the function goes to catch
            arrivalchoicevalue = arrivalchoice.getValue();
            departurechoicevalue = departurechoice.getValue();
            daycountvalue = ChronoUnit.DAYS.between(arrivalchoicevalue, departurechoicevalue);
            // Daycount return 1 day if arrival and departure at same day
            if (daycountvalue == 0) {
                daycountvalue = 1;
            }
            // Other warnings appear if other conditions are not fulfilled
            if (areachoicevalue == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Area missing");
                alert.setContentText("Please choose a preferred area");
                alert.showAndWait();
            }
            if (maxpricevalue == -1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Price range missing");
                alert.setContentText("Please choose a price range");
                alert.showAndWait();
            }
            if (guestnumbervalue == -1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Number of guests missing");
                alert.setContentText("Please choose number of guests");
                alert.showAndWait();
            }
            if (daycountvalue < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Do you travel backwards in time?");
                alert.setContentText("Please select a departure date that is not before the arrival date");
                alert.showAndWait();
            }
            // If everything is all right, values from the comboboxes used to determine the search results
            // Hotels are fetched based on the conditions chosen in the search
            else {
                hotelResults = FXCollections.observableArrayList(search.hotelSearch(areachoicevalue, guestnumbervalue, maxpricevalue));
                resultList.setItems(hotelResults);
            }
        } catch (Exception e) {
            // Warning appears for nullpointerexception considering length of stay
            if (arrivalchoicevalue == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Arrival date missing");
                alert.setContentText("Please choose an arrival date");
                alert.showAndWait();
            }
            if (departurechoicevalue == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Departure date missing");
                alert.setContentText("Please choose a departure date");
                alert.showAndWait();
            }
        }
    }

    // Shows a window where the user can register for a postlist
    public void mailListDialogHandler(ActionEvent actionEvent) {
        mailListController.mailDialog(mailID.getText());
    }

    /*
     * Observes changed values of an area - puts the name of a chosen area into the areachoicevalue instance variable
     */
    private void areaChoiceHandler() {
        areachoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    areachoicevalue = newValue;
                    System.out.println(areachoicevalue);
                });
    }

    /*
     * Observes changed values of the maximum price - updates the maxpricevalue instance variable accordingly
     */
    private void priceChoiceHandler() {
        pricechoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    if (newValue.equals("25000 ISK or less")) {
                        maxpricevalue = 25000;
                    } else if (newValue.equals("35000 ISK or less")) {
                        maxpricevalue = 35000;
                    } else if (newValue.equals("45000 ISK or less")) {
                        maxpricevalue = 45000;
                    } else if (newValue.equals("60000 ISK or less")) {
                        maxpricevalue = 60000;
                    } else if (newValue.equals("Doesn't matter")) {
                        maxpricevalue = Integer.MAX_VALUE;
                    }
                });
    }

    /*
     * Observes changed values of guest number - updates the guestnumbervalue instance variable accordingly
     */
    private void guestNumberHandler() {
        guestnumber.getSelectionModel()
                .selectedItemProperty()
                .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    } else if (newValue == "More") {
                        guestnumbervalue = 5;
                    } else {
                        guestnumbervalue = Integer.parseInt(newValue);
                    }

                });
    }

    /*
     * Handler for choosing a certain hotel from the search results.
     * The hotel chosen is "saved" and the next window shown.
     * If no hotel is chosen, a warning appears.
     */
    public void chooseHotelHandler(ActionEvent actionEvent) throws IOException {
        if (hotelindex != -1) {
            chosenHotel = (Hotel) resultList.getItems().get(hotelindex);
            // New scene is loaded - herbergi.fxml
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("herbergi.fxml"));
            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            HerbergiController herbergi = Loader.getController();
            herbergi.setValues(chosenHotel, daycountvalue, arrivalchoicevalue, departurechoicevalue, guestnumbervalue);
            herbergi.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
            Parent herbergi_parent = Loader.getRoot();
            Scene herbergi_scene = new Scene(herbergi_parent);
            Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            main_stage.setScene(herbergi_scene);
            main_stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No hotel chosen");
            alert.setContentText("Please choose a hotel");
            alert.showAndWait();
        }

    }

    /*
     * A function that determines how the result list should be sorted based on the choice of radiobutton
     */
    public void sortingHandler(ActionEvent actionEvent) {
        System.out.println("sorting handler hotel results:" + hotelResults);
        RadioButton r = (RadioButton) actionEvent.getSource();

        if (hotelResults == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No hotel found");
            alert.setContentText("Please search for a hotel");
            alert.showAndWait();
        } else {

            if (Integer.parseInt(r.getId()) == 1) {
                sortByPrice();
            } else if (Integer.parseInt(r.getId()) == 2) {
                sortByReviews();
            } else if (Integer.parseInt(r.getId()) == 3) {
                sortByStars();
            }
        }
    }

    /*
     * A function that sorts the result list based on the minimum room price of each hotel
     */
    public void sortByPrice() {

        hotelResults = FXCollections.observableArrayList(search.priceSort(hotelResults));

        for (Object hotel : hotelResults) {
            hotel.toString();
        }
        resultList.setItems(hotelResults);
    }

    /*
     * A function that sorts the result list based on the number of reviews for each hotel
     */
    public void sortByReviews() {

        hotelResults = FXCollections.observableArrayList(search.reviewSort(hotelResults));

        resultList.setItems(hotelResults);

    }

    /*
     * A function that sorts the result list based on the number of stars for each hotel
     */
    public void sortByStars() {

        hotelResults = FXCollections.observableArrayList(search.starSort(hotelResults));

        resultList.setItems(hotelResults);

    }

    public void setSaveInfo(String Firstname, String Lastname, String Email, String Phone, String Address, String Kennitala, String Card) {
        firstname = Firstname;
        lastname = Lastname;
        email = Email;
        phone = Phone;
        address = Address;
        kennitala = Kennitala;
        card = Card;
    }

}
