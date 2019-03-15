package is.hi.hbv.utlit;
import com.sun.jndi.toolkit.url.UrlUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import is.hi.hbv.vinnsla.Hotel;
import is.hi.hbv.vinnsla.HotelsDAO;
import javafx.beans.value.ObservableValue;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HerbergiController implements Initializable{
    @FXML
    private MenuItem pin_info;

    @FXML
    private Slider zoom_slider;

    @FXML
    private ScrollPane map_scrollpane;

    @FXML
    private ListView<String> test;

    @FXML
    private Label hotelname;

    @FXML
    private TextArea hotelInfo;

    @FXML
    private Pane hotelImage;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String kennitala;
    private String card;

    Group zoomGroup;

    private Hotel chosenHotel;
    private long daycountvalue;
    private LocalDate arrivalchoicevalue;
    private LocalDate departurechoicevalue;
    private int guestnumbervalue;

    ObservableList<String> items = FXCollections.observableArrayList("test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2");

    // Sækja morepic glugga
    @FXML
    void morePic(ActionEvent actionEvent) throws IOException {
        System.out.println("Hótel? " + chosenHotel);
        // Loadum nýrri glugga -> MorePic.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MorePic.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("More Picture");
        stage.setScene(new Scene(root1));
        stage.show();
    }


    // ZoomIn aðferð
    @FXML
    void zoomIn(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    // ZoomOut aðferð
    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }

    // Sækja search glugga
    @FXML
    void returnSearch(ActionEvent actionEvent) throws IOException {
        // Loadum nýrri scene -> Search.fxml
        Parent Search_parent = FXMLLoader.load(getClass().getResource("Search.fxml"));
        Scene Search_scene = new Scene(Search_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setScene(Search_scene);
        main_stage.show();
    }
    // Sækja þjónusta glugga
    @FXML
    void nextPage(ActionEvent actionEvent) throws IOException {
        // Loadum nýrri scene -> payment.fxml

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("services.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }


        servicesController room = Loader.getController();
        //room.setHotel(chosenHotel);
        room.setValues(chosenHotel, daycountvalue, arrivalchoicevalue, departurechoicevalue, guestnumbervalue);
        room.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
        Parent payment_parent = Loader.getRoot();

        //Parent payment_parent = FXMLLoader.load(getClass().getResource("services.fxml"));
        Scene payment_scene = new Scene(payment_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setTitle("Services");
        main_stage.setScene(payment_scene);
        main_stage.show();
    }


    @FXML
    void initialize() {
        //System.out.println("Valið hótel: " + chosenHotel);
        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        // sitja default 1.0 í stillingar svo notenda getur auðvelt að zoomin og OUT
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));

        // Wrap scroll content in a Group so ScrollPane re-computes scroll bars
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
    }
    // Sækja X og Y value í pane til að sitja nýja gildi í zoomið
    private void zoom(double scaleValue) {
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    public void setChosenHotel(Hotel hotel) {
        chosenHotel = hotel;
        hotelname.setText(chosenHotel.getName());
        hotelInfo.setText(chosenHotel.getHotelInfo());
        System.out.println("Hótelið er: " + chosenHotel);
        showRooms();

        // Birtir mynd af völdu hóteli.
        hotelImage.getChildren().clear();
        Integer hotelID = chosenHotel.getHotelID();
        Image image = new Image("is/hi/hbv/utlit/img/Roomimage/hotel/" + hotelID + ".jpg");
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        imageView.setFitWidth(383);
        imageView.setFitHeight(212);
        hotelImage.getChildren().add(imageView);
    }

    public void setValues(Hotel hotel, long daycount, LocalDate arrival, LocalDate departure, int guests) {
        chosenHotel = hotel;
        daycountvalue = daycount;
        arrivalchoicevalue = arrival;
        departurechoicevalue = departure;
        guestnumbervalue = guests;
    }


    public void showRooms() {
        ArrayList<Object> rooms = chosenHotel.getRooms();
        for (Object room : rooms) {
            System.out.println(room);
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        test.setItems(items);
    }
}
