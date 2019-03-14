package is.hi.hbv.utlit;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
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
import java.time.temporal.ChronoUnit;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    public ScrollPane resultboxScroll;  // Skrollbox fyrir niðurstöður , þarf ekki ég búinn henda það
    @FXML
    public VBox resultboxPlane; // Annað box fyrir niðurstöður, spurning hvort þurfi , þarf ekki ég búinn henda það
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

    private ObservableList<String> priceList = FXCollections.observableArrayList("15000 ISK or less", "25000 ISK or less", "35000 ISK or less", "45000 ISK or less", "60000 ISK or less", "Doesn't matter");

    private ObservableList<String> guestList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6");

    private String areachoicevalue;    // Gildi fyrir valið svæði
    private int maxpricevalue;         // Gildi fyrir hámarksverð
    private int guestnumbervalue;      // Gildi fyrir valinn gestafjölda
    private LocalDate arrivalchoicevalue; // Gildi fyrir valinn komudag
    private LocalDate departurechoicevalue; // Gildir fyrir valinn brottfarardag
    private long daycountvalue;          // Gildi fyrir fjölda gistinátta
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
        hotelResults = FXCollections.observableArrayList(database.getHotelsSearchA(maxpricevalue, areachoicevalue, guestnumbervalue));
        // Viljum birta strengjaútgáfu af objectinu
        for (Object hotel : hotelResults) {
            hotel.toString();
        }
        // Uppfærum niðurstöðuglugann
        resultList.setItems(hotelResults);


        // Svona náum við í dagsetningarnar úr datepickers - uppfærum tilviksbreyturnar

        arrivalchoicevalue = arrivalchoice.getValue();
        // System.out.println(arrivalchoicevalue);
        departurechoicevalue = departurechoice.getValue();
        // System.out.println(departurechoicevalue);
        daycountvalue = ChronoUnit.DAYS.between(arrivalchoicevalue, departurechoicevalue);
        // System.out.println(daycountvalue);
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
                    if (newValue.equals("25000 ISK or less")) {
                        //minpricevalue = 15000;
                        maxpricevalue = 25000;
                    }
                    else if (newValue.equals("35000 ISK or less")) {
                        //minpricevalue = 25000;
                        maxpricevalue = 35000;
                    }
                    else if (newValue.equals("45000 ISK or less")) {
                        //minpricevalue = 35000;
                        maxpricevalue = 45000;
                    }
                    else if (newValue.equals("60000 ISK or less")) {
                        //minpricevalue = 45000;
                        maxpricevalue = 60000;
                    }
                    else if (newValue.equals("Doesn't matter")) {
                        //minpricevalue = 0;
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
            hotelName = String.valueOf(resultList.getItems().get(hotelindex)); //TODO: Má sleppa þessu.
            chosenHotel = (Hotel) resultList.getItems().get(hotelindex);
            // System.out.println("Valið hótel: " + chosenHotel);
            // System.out.println("Herbergi í völdu hóteli: " + chosenHotel.getRooms());
        }
        // Loadum nýrri senu -> Herbergi.fxml

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("herbergi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HerbergiController herbergi = Loader.getController();

        herbergi.setChosenHotel(chosenHotel);
        herbergi.setValues(chosenHotel, daycountvalue, arrivalchoicevalue, departurechoicevalue, guestnumbervalue);
        herbergi.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
        //herbergi.printHotel(chosenHotel);

        Parent herbergi_parent = Loader.getRoot();
        //Stage stage = new Stage();
        //stage.setScene(new Scene(p));
        //stage.show();

        // gamla:
        // Parent herbergi_parent = FXMLLoader.load(getClass().getResource("herbergi.fxml"));
        Scene herbergi_scene = new Scene(herbergi_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setScene(herbergi_scene);
        main_stage.show();

        //TODO: Loada herbergjum úr völdu hóteli inn í næstu senu. Annað hvort Hotel hlutnum, og herbergiController kallar á getRooms(), eða Room listanum úr getRooms.
        //TODO: Loada hotelID/hotelInfo inn í showHotelInfo í herbergiController
        // TODO: Ný hugmynd - setja chosenHotel inn í set fall í herbergiController - þá getur hann notað það fyrir allt.
        // TODO: Þarf reyndar líka að koma fjölda daga þarna inn líka, og kannski upphafs- og lokadagsetningu.
    }

    /*
    * Fall sem ákvarðar sorteringu fyrir val á radiobuttons
     */
    // TODO: Setja inn valmöguleika fyrir radiobutton 3 -> sortByReviews
    public void sortingHandler(ActionEvent actionEvent) {
        RadioButton r = (RadioButton)actionEvent.getSource();
        if (Integer.parseInt(r.getId()) == 1) {
            sortByPrice();
        }
        else if (Integer.parseInt(r.getId()) == 3) {
            sortByStars();
        }
    }

    /*
    * Fall sem endurraðar niðurstöðulista eftir verði.
     */
    public void sortByPrice() {

        ArrayList<Object> hotelsPriceSorted = new ArrayList<Object>();
        int [] hotelPriceSort = new int[hotelResults.size()];
        // Setjum öll verðgildin inn í venjulegt fylki, hotelPriceSort
        int index = 0;
        for (Object hotel : hotelResults) {
            Hotel nHotel = (Hotel) hotel;
            hotelPriceSort[index] = nHotel.getPrice();
            index++;
        }
        // Nú sorterum við hotelSort fylkið:
        int tmp;
        for (int count = 1; count < hotelPriceSort.length; count++) {
            for (int i = 0; i < hotelPriceSort.length - 1; i++) {
                if (hotelPriceSort[i] > hotelPriceSort[i+1]) {
                    tmp = hotelPriceSort[i];
                    hotelPriceSort[i] = hotelPriceSort[i+1];
                    hotelPriceSort[i+1] = tmp;
                }
            }
        }
        // Svo finnum við samsvarandi verðgildi í hotelResult listanum og bætum í röð inn í hotelsSorted
        for (int j = 0; j < hotelPriceSort.length; j++) {
            for (Object hotel : hotelResults) {
                Hotel aHotel = (Hotel) hotel;
                if (aHotel.getPrice() == hotelPriceSort[j]) {
                    hotelsPriceSorted.add(aHotel);
                }
            }
        }
        // Breytum yfir á viðeigandi form og birtum uppfærðan, raðaðan niðurstöðulista.
        hotelResults = FXCollections.observableArrayList(hotelsPriceSorted);

        for (Object hotel : hotelResults) {
            hotel.toString();
        }

        resultList.setItems(hotelResults);

    }

    public void sortByStars() {

        ArrayList<Object> hotelsStarSorted = new ArrayList<Object>();
        int [] hotelStarSort = new int[hotelResults.size()];
        // Setjum öll stjörnugildin inn í venjulegt fylki, hotelSort
        int index = 0;
        for (Object hotel : hotelResults) {
            Hotel nHotel = (Hotel) hotel;
            int stars = nHotel.getStars();
            System.out.println(stars);
            hotelStarSort[index] = nHotel.getStars();
            hotelStarSort[index] = stars;
            index++;
        }

        // Nú sorterum við hotelSort fylkið:
        int tmp;
        for (int count = 1; count < hotelStarSort.length; count++) {
            for (int i = 0; i < hotelStarSort.length - 1; i++) {
                if (hotelStarSort[i] > hotelStarSort[i+1]) {
                    tmp = hotelStarSort[i];
                    hotelStarSort[i] = hotelStarSort[i+1];
                    hotelStarSort[i+1] = tmp;
                }
            }
        }
        // Þetta er ennþá rétta lengdin
        for (int i = 0; i < hotelStarSort.length; i++) {
            System.out.println(hotelStarSort[i]);
        }

        // Svo finnum við samsvarandi stjörnufjölda í hotelResult listanum og bætum í röð inn í hotelsSorted
        for (int j = 0; j < hotelStarSort.length; j++) {
            for (Object hotel : hotelResults) {
                Hotel aHotel = (Hotel) hotel;
                if (aHotel.getStars() == hotelStarSort[j] && !hotelsStarSorted.contains(hotel)) {
                    hotelsStarSorted.add(aHotel);
                }
            }
        }
        // Breytum yfir á viðeigandi form og birtum uppfærðan, raðaðan niðurstöðulista.
        hotelResults = FXCollections.observableArrayList(hotelsStarSorted);

        // Hér er hotelResults búið að doublast

        // for (Object hotel : hotelResults) {
        //    hotel.toString();
        // }

        resultList.setItems(hotelResults);

        // Hér er hotelResults orðið double
    }

    //TODO: Búa til sortByReviews!

    // Skilar object fyrir valið hótel (herbergi) í lista
    public Hotel getChosenHotel() {
        return chosenHotel;
    }

    public long getDaycountvalue() {
        return daycountvalue;
    }

    public LocalDate getArrivalDate() {
        return arrivalchoicevalue;
    }
    public LocalDate getDepartureDate() {
        return departurechoicevalue;
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
