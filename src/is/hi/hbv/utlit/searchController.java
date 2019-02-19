package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.Hotel;
import is.hi.hbv.vinnsla.HotelsDAO;
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
import java.util.ArrayList;
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
    public AnchorPane mailList; // Glugginn fyrir póstlista - óþarfi?
    @FXML
    public ListView resultList; // Listinn fyrir niðurstöður
    @FXML
    public Button chooseButton; // Takki til að velja hótel í niðurstöðum
    @FXML
    public RadioButton priceRadio; // Radiobutton til að sortera eftir verði
    @FXML
    public RadioButton reviewRadio; // Radiobutton til að sortera eftir reviews
    @FXML
    public RadioButton starRadio; // Radiobutton til að sortera eftir fjölda stjarna
    @FXML
    private MailListController mailListController; // Tilvik fyrir póstlista dialog controller

    Hotel chosenHotel; // Valið hótel (herbergi) í lista - object

    int hotelindex = 0; //Núverandi index í result lista

    String hotelName; // Nafn valins hótels í lista.

    private ObservableList<Object> hotelResults; // Niðurstöður fyrir valin hótel

    // Listar fyrir mismunandi svæði, verð og gestafjölda sem er hægt að velja í drop down listum
    private ObservableList<String> areaList = FXCollections.observableArrayList("Capital area", "North", "South", "East", "West", "All areas");

    private ObservableList<String> priceList = FXCollections.observableArrayList("15000 ISK or less", "15000-25000 ISK", "25000-35000 ISK", "35000-45000 ISK", "45000-60000 ISK", "More than 60000 ISK", "Doesn't matter");

    private ObservableList<String> guestList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6");

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

        // Fylgjumst með völdum gildum í niðurstöðuglugga
        MultipleSelectionModel<Object> lsm = resultList.getSelectionModel();
        lsm.selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
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
        // Náum í hótelherbergi eftir skilyrðum sem við veljum í search
        HotelsDAO database = new HotelsDAO();
        // Gamla kommentað
        // ObservableList<String> hotelResults = FXCollections.observableArrayList(database.getHotelsSearch(minpricevalue, maxpricevalue, areachoicevalue, guestnumbervalue));
        // Nýja - skilar objects
        hotelResults = FXCollections.observableArrayList(database.getHotelsSearchA(minpricevalue, maxpricevalue, areachoicevalue, guestnumbervalue));
        // Viljum birta strengjaútgáfu af objectinu
        for (Object hotel : hotelResults) {
            hotel.toString();
        }
        // Uppfærum niðurstöðuglugann
        resultList.setItems(hotelResults);


        // Svona náum við í dagsetningarnar úr datepickers - uppfærum tilviksbreyturnar

        // TODO: Eftir að finna út hvernig skal tengja þetta við gagnagrunninn!
        arrivalchoicevalue = arrivalchoice.getValue();
        System.out.println(arrivalchoicevalue);
        departurechoicevalue = departurechoice.getValue();
        System.out.println(departurechoicevalue);

    }
    // Birtir glugga til að skrá sig á póstlista
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
                    System.out.println(areachoicevalue);
                });
    }
    /*
     * Fylgist með breyttum gildum á vali á verðbili - skilar int gildi inn í tilsvarandi tilviksbreytur
     */
    private void priceChoiceHandler() {
        pricechoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    if (newValue.equals("Less than 15000ISK"))  {
                        minpricevalue = 0;
                        maxpricevalue = 15000;
                    } else if (newValue.equals("15000-25000 ISK")) {
                        minpricevalue = 15000;
                        maxpricevalue = 25000;
                    }
                    else if (newValue.equals("25000-35000 ISK")) {
                        minpricevalue = 25000;
                        maxpricevalue = 35000;
                    }
                    else if (newValue.equals("35000-45000 ISK")) {
                        minpricevalue = 35000;
                        maxpricevalue = 45000;
                    }
                    else if (newValue.equals("45000-60000 ISK")) {
                        minpricevalue = 45000;
                        maxpricevalue = 60000;
                    }
                    else if (newValue.equals("More than 60000 ISK")) {
                        minpricevalue = 60000;
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
                    guestnumbervalue = Integer.parseInt(newValue);

                    System.out.println(guestnumbervalue);
                });
    }

    /*
    * Handler fyrir að velja hótel úr leitarniðurstöðum.
    * Nafnið á hótelinu sem er valið í listanum að því sinni er vistað og herbergi.fxml er birt.
    * Einnig er hótel objectinn sjálfur vistaður. (Bætt við seinna, sennilega gáfulegra).
    * Hægt að ná í hótelnafnið með getHotelName() og hótel objectinn með getHotel() hér fyrir neðan.
     */
    public void chooseHotelHandler(ActionEvent actionEvent) throws IOException {
        if (hotelindex != -1) {
            hotelName = String.valueOf(resultList.getItems().get(hotelindex));
            System.out.println("Nafnið á völdu hóteli: " + hotelName);
            chosenHotel = (Hotel) resultList.getItems().get(hotelindex);
            System.out.println("Valið hótel: " + chosenHotel);
        }
        // Loadum nýrri senu -> Herbergi.fxml
        Parent herbergi_parent = FXMLLoader.load(getClass().getResource("herbergi.fxml"));
        Scene herbergi_scene = new Scene(herbergi_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setScene(herbergi_scene);
        main_stage.show();

    }

    /*
    * Fall sem ákvarðar sorteringu fyrir val á radiobuttons
    * TODO: Útfæra föll til að raða eftir fjölda stjarna og fjölda reviews
     */
    public void sortingHandler(ActionEvent actionEvent) {
        RadioButton r = (RadioButton)actionEvent.getSource();
        if (Integer.parseInt(r.getId()) == 1) {
            sortByPrice();
        }

    }

    /*
    * Fall sem endurraðar niðurstöðulista eftir verði.
     */
    public void sortByPrice() {

        ArrayList<Object> hotelsSorted = new ArrayList<Object>();
        int [] hotelSort = new int[hotelResults.size()];
        // Setjum öll verðgildin inn í venjulegt fylki, hotelSort
        int index = 0;
        for (Object hotel : hotelResults) {
            Hotel nHotel = (Hotel) hotel;
            hotelSort[index] = nHotel.getPrice();
            index++;
        }
        // Nú sorterum við hotelSort fylkið:
        int tmp;
        for (int count = 1; count < hotelSort.length; count++) {
            for (int i = 0; i < hotelSort.length - 1; i++) {
                if (hotelSort[i] > hotelSort[i+1]) {
                    tmp = hotelSort[i];
                    hotelSort[i] = hotelSort[i+1];
                    hotelSort[i+1] = tmp;
                }
            }
        }
        // Svo finnum við samsvarandi verðgildi í hotelResult listanum og bætum í röð inn í hotelsSorted
        for (int j = 0; j < hotelSort.length; j++) {
            for (Object hotel : hotelResults) {
                Hotel aHotel = (Hotel) hotel;
                if (aHotel.getPrice() == hotelSort[j]) {
                    hotelsSorted.add(aHotel);
                }
            }
        }
        // Breytum yfir á viðeigandi form og birtum uppfærðan, raðaðan niðurstöðulista.
        hotelResults = FXCollections.observableArrayList(hotelsSorted);

        for (Object hotel : hotelResults) {
            hotel.toString();
        }

        resultList.setItems(hotelResults);
    }
    // Skilar nafni á völdu hóteli (herbergi) í lista
    public String getHotelName() {
        return hotelName;
    }
    // Skilar object fyrir valið hótel (herbergi) í lista
    public Hotel getHotel() {
        return chosenHotel;
    }

}
