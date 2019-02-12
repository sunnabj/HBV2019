package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.HotelsDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;


public class searchController implements Initializable {
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
    @FXML
    public ScrollPane resultboxScroll;
    @FXML
    public VBox resultboxPlane;
    @FXML
    public ToggleGroup sorting;
    @FXML
    public AnchorPane mailList;
    @FXML
    public ListView resultList;
    @FXML
    private MailListController mailListController;

    private ObservableList<String> areaList = FXCollections.observableArrayList("Capital area", "North", "South", "East", "West", "All areas");

    private ObservableList<String> priceList = FXCollections.observableArrayList("Less than 3000 ISK", "3000-6000 ISK", "6000-10000 ISK", "10000-15000 ISK", "15000-20000 ISK", "More than 20000 ISK", "Doesn't matter");

    private ObservableList<String> guestList = FXCollections.observableArrayList("1", "2", "3 or more");

    private int areachoicevalue;
    private int pricechoicevalue;
    private int guestnumbervalue;
    private LocalDate arrivalchoicevalue;
    private LocalDate departurechoicevalue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        areachoice.setValue("Choose an area...");
        areachoice.setItems(areaList);
        pricechoice.setValue("Price per night");
        pricechoice.setItems(priceList);
        guestnumber.setValue("Choose number of guests...");
        guestnumber.setItems(guestList);
        areaChoiceHandler();
        priceChoiceHandler();
        guestNumberHandler();
        // mailController = new MailListController(); // Spurning
        // mailController.initSearch(this); // var að taka
    }

    public void hotelsearchHandler(ActionEvent actionEvent) {
        // Notum value sem við fáum úr comboboxunum til að ákvarða hvað birtist í leitarniðurstöðunum.
        // Hér er test sem nær í gögn úr gagnagrunninum :)
        HotelsDAO database = new HotelsDAO();
        // Með næstu skipun koma inn bara öll hótel
        // ObservableList<String> hotelResults = FXCollections.observableArrayList(database.getHotelNames());
        // Hér hins vegar náum við í hótelherbergi
        ObservableList<String> hotelResults = FXCollections.observableArrayList(database.getHotelRooms());
        System.out.println(hotelResults);
        // Hér birtum við annað hvort á listanum
        resultList.setItems(hotelResults);
        // Svona náum við í dagsetningarnar úr datepickers - uppfærum tilviksbreyturnar
        arrivalchoicevalue = arrivalchoice.getValue();
        System.out.println(arrivalchoicevalue);
        departurechoicevalue = departurechoice.getValue();
        System.out.println(departurechoicevalue);
        // Hér getum við valið og birt herbergi eftir verði. Hægt að útvíkka þetta!
        ObservableList<String> roomsbyPrice = FXCollections.observableArrayList(database.getRoomsbyPrice(pricechoicevalue));
        resultList.setItems(roomsbyPrice);
        System.out.println(roomsbyPrice);
    }

    public void mailListDialogHandler(ActionEvent actionEvent) {
        // Helvítis vesen á þessu! mailListController alltaf null.
        System.out.println(mailListController);
        mailListController.mailDialog();
    }

    /*
    * Fylgist með breyttum gildum á vali á landsvæði - skilar int gildi inn í tilsvarandi tilviksbreytu
     */
    private void areaChoiceHandler() {
        areachoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    if (newValue.equals("Capital area"))  {
                        areachoicevalue = 0;
                    } else if (newValue.equals("North")) {
                        areachoicevalue = 1;
                    }
                    else if (newValue.equals("South")) {
                        areachoicevalue = 2;
                    }
                    else if (newValue.equals("East")) {
                        areachoicevalue = 3;
                    }
                    else if (newValue.equals("West")) {
                        areachoicevalue = 4;
                    }
                    else if (newValue.equals("All areas")) {
                        areachoicevalue = 5;
                    }
                    System.out.println(areachoicevalue);
                });
    }
    /*
     * Fylgist með breyttum gildum á vali á verðbili - skilar int gildi inn í tilsvarandi tilviksbreytu
     */
    private void priceChoiceHandler() {
        pricechoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    if (newValue.equals("Less than 3000ISK"))  {
                        pricechoicevalue = 0;
                    } else if (newValue.equals("3000-6000 ISK")) {
                        pricechoicevalue = 1;
                    }
                    else if (newValue.equals("6000-10000 ISK")) {
                        pricechoicevalue = 2;
                    }
                    else if (newValue.equals("10000-15000 ISK")) {
                        pricechoicevalue = 3;
                    }
                    else if (newValue.equals("15000-20000 ISK")) {
                        pricechoicevalue = 4;
                    }
                    else if (newValue.equals("More than 20000 ISK")) {
                        pricechoicevalue = 5;
                    }
                    else if (newValue.equals("Doesn't matter")) {
                        pricechoicevalue = 6;
                    }
                    System.out.println(pricechoicevalue);
                });
    }
    /*
     * Fylgist með breyttum gildum á vali á gestafjölda - skilar int gildi inn í tilsvarandi tilviksbreytu
     */
    private void guestNumberHandler() {
        guestnumber.getSelectionModel()
                .selectedItemProperty()
                .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    if (newValue.equals("1"))  {
                        guestnumbervalue = 0;
                    } else if (newValue.equals("2")) {
                        guestnumbervalue = 1;
                    }
                    else if (newValue.equals("3 or more")) {
                        guestnumbervalue = 2;
                    }

                    System.out.println(guestnumbervalue);
                });
    }



}
