package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.HotelsDAO;
import is.hi.hbv.vinnsla.RoomsDAO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class searchController implements Initializable {
    @FXML
    public DatePicker arrivalchoice; //Val á komudegi
    @FXML
    public DatePicker departurechoice; //Val á brottfarardegi
    @FXML
    public ComboBox areachoice; // Val á landsvæði
    @FXML
    public ComboBox pricechoice; // Val á verðbili
    @FXML
    public ComboBox guestnumber; // Val á gestafjölda
    @FXML
    public Button searchButton; // Takki til að hefja leit
    @FXML
    public Button mailListButton; // Takki til að skrá sig á póstlista
    @FXML
    public AnchorPane frontPageAnchor;  // Pane utan um alla forsíðuna
    @FXML
    public ScrollPane resultboxScroll;  // Skrollbox fyrir niðurstöður
    @FXML
    public VBox resultboxPlane; // Annað box fyrir niðurstöður, spurning hvort þurfi
    @FXML
    public ToggleGroup sorting; // Til að geta bara valið eitt sorting skilyrði í einu
    @FXML
    public AnchorPane mailList; // hmmm
    @FXML
    public ListView resultList; // Listinn fyrir niðurstöður
    @FXML
    public Button chooseButton; // Takki til að velja hótel í niðurstöðum
    @FXML
    private MailListController mailListController; // Tilvik fyrir póstlista dialog controller - vesen

    int hotelindex = 0; //Núverandi index í result lista

    String hotelName; // Nafn valins hótels í lista.

    // Listar fyrir mismunandi svæði, verð og gestafjölda sem er hægt að velja í drop down listum
    private ObservableList<String> areaList = FXCollections.observableArrayList("Capital area", "North", "South", "East", "West", "All areas");

    private ObservableList<String> priceList = FXCollections.observableArrayList("3000 ISK or less", "3000-6000 ISK", "6000-10000 ISK", "10000-15000 ISK", "15000-20000 ISK", "More than 20000 ISK", "Doesn't matter");

    private ObservableList<String> guestList = FXCollections.observableArrayList("1", "2", "3 or more");

    private String areachoicevalue;    // Gildi fyrir valið svæði
    private int maxpricevalue;         // Gildi fyrir hærri mörk verðbils
    private int minpricevalue;         // Gildi fyrir lægri mörk verðbils
    private int guestnumbervalue;      // Gildi fyrir valinn gestafjölda
    private LocalDate arrivalchoicevalue; // Gildi fyrir valinn komudag
    private LocalDate departurechoicevalue; // Gildir fyrir valinn brottfarardag

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Dropdown val-listarnir frumstilltir
        areachoice.setValue("Choose an area...");
        areachoice.setItems(areaList);
        pricechoice.setValue("Price per night");
        pricechoice.setItems(priceList);
        guestnumber.setValue("Choose number of guests...");
        guestnumber.setItems(guestList);
        // Frumstillum handlera fyrir þá
        areaChoiceHandler();
        priceChoiceHandler();
        guestNumberHandler();

        MultipleSelectionModel<String> lsm = resultList.getSelectionModel();
        lsm.selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Indexinn í listanum.
                hotelindex = lsm.getSelectedIndex();
            }
        });
    }
    /*
    * Það sem gerist þegar ýtt er á leita takkann - ýmislegt í boði.
     */
    public void hotelsearchHandler(ActionEvent actionEvent) {
        // Notum value sem við fáum úr comboboxunum til að ákvarða hvað birtist í leitarniðurstöðunum.
        // Náum í hótelherbergi eftir verði sem við veljum í search
        HotelsDAO database = new HotelsDAO();
        ObservableList<String> hotelResults = FXCollections.observableArrayList(database.getHotelsbyPrice(minpricevalue, maxpricevalue));
        // Birtum þá svo á listanum fyrir leitarniðurstöður
        resultList.setItems(hotelResults);
        // Eftir að útvíkka þetta fyrir fleiri leitarmöguleika.

        // RoomsDAO databaseb = new RoomsDAO();
        // Svona náum við í dagsetningarnar úr datepickers - uppfærum tilviksbreyturnar
        arrivalchoicevalue = arrivalchoice.getValue();
        System.out.println(arrivalchoicevalue);
        departurechoicevalue = departurechoice.getValue();
        System.out.println(departurechoicevalue);

    }

    public void mailListDialogHandler(ActionEvent actionEvent) {
        mailListController.mailDialog();
    }

    /*
    * Fylgist með breyttum gildum á vali á landsvæði - skilar nafni landsvæðis inn í tilsvarandi tilviksbreytu
     */
    private void areaChoiceHandler() {
        areachoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    areachoicevalue = newValue;
                    /*
                    if (newValue.equals("Capital area"))  {
                        areachoicevalue = "Capital";
                    } else if (newValue.equals("North")) {
                        areachoicevalue = "North";
                    }
                    else if (newValue.equals("South")) {
                        areachoicevalue = "South";
                    }
                    else if (newValue.equals("East")) {
                        areachoicevalue = "East";
                    }
                    else if (newValue.equals("West")) {
                        areachoicevalue = "West";
                    }
                    else if (newValue.equals("All areas")) {
                        areachoicevalue = "All"; // Spurning
                    }
                    */
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
                        minpricevalue = 0;
                        maxpricevalue = 3000;
                    } else if (newValue.equals("3000-6000 ISK")) {
                        minpricevalue = 3000;
                        maxpricevalue = 6000;
                    }
                    else if (newValue.equals("6000-10000 ISK")) {
                        minpricevalue = 6000;
                        maxpricevalue = 10000;
                    }
                    else if (newValue.equals("10000-15000 ISK")) {
                        minpricevalue = 10000;
                        maxpricevalue = 15000;
                    }
                    else if (newValue.equals("15000-20000 ISK")) {
                        minpricevalue = 15000;
                        maxpricevalue = 20000;
                    }
                    else if (newValue.equals("More than 20000 ISK")) {
                        minpricevalue = 20000;
                        maxpricevalue = 1000000000;
                    }
                    else if (newValue.equals("Doesn't matter")) {
                        minpricevalue = 0;
                        maxpricevalue = 1000000000;
                    }
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

    /*
    * Handler fyrir að velja hótel úr leitarniðurstöðum.
    * Nafnið á hótelinu sem er valið í listanum að því sinni er vistað og herbergi.fxml er birt.
    * Hægt að ná í hótelnafnið með getHotelName() hér fyrir neðan.
     */
    public void chooseHotelHandler(ActionEvent actionEvent) throws IOException {
        if (hotelindex != -1) {
            hotelName = String.valueOf(resultList.getItems().get(hotelindex));
            System.out.println("Nafnið á völdu hóteli: " + hotelName);
        }
        Parent herbergi_parent = FXMLLoader.load(getClass().getResource("herbergi.fxml"));
        Scene herbergi_scene = new Scene(herbergi_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setScene(herbergi_scene);
        main_stage.show();

    }

    public String getHotelName() {
        return hotelName;
    }
}
