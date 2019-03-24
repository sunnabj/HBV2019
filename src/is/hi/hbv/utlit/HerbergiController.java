package is.hi.hbv.utlit;
import com.sun.jndi.toolkit.url.UrlUtil;
import is.hi.hbv.vinnsla.Room;
import javafx.animation.ParallelTransition;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    private Button outID;

    @FXML
    private Button backID;

    @FXML
    private Slider zoom_slider;

    @FXML
    private ScrollPane map_scrollpane;

    @FXML

    private ListView hotelRooms; // var <String>

    @FXML
    private Label hotelname;

    @FXML
    private Button inID;

    @FXML
    private TextArea hotelInfo;

    @FXML
    private Button nextID;

    @FXML
    private WebView mapID;

    @FXML
    private Pane hotelImage;

    @FXML
    private Button morepicID;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String kennitala;
    private String card;

    Group zoomGroup;

    int roomindex = -1;

    private Hotel chosenHotel;
    private Room chosenRoom;
    private long daycountvalue;
    private LocalDate arrivalchoicevalue;
    private LocalDate departurechoicevalue;
    private int guestnumbervalue;

    private ObservableList<Object> roomResults;


    //ObservableList<String> items = FXCollections.observableArrayList("test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //test.setItems(items);
        //outID.setTooltip(new Tooltip("Zoom-out"));
        //inID.setTooltip(new Tooltip("Zoom-in."));
        nextID.setTooltip(new Tooltip("Go to services"));
        backID.setTooltip(new Tooltip("Go back to search"));

        // Fylgjumst með völdum gildum í niðurstöðuglugga
        MultipleSelectionModel<Object> lsm = hotelRooms.getSelectionModel();
        lsm.selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                // Indexinn í listanum.
                roomindex = lsm.getSelectedIndex();
                System.out.println("Room index: " + roomindex);
            }
        });

    }
/*
    @FXML
    public void initialize() {
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
        mapID.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("maplist.fxml"));
                    Parent root = (Parent) loader.load();
                    Stage stage = new Stage();
                    //stage.initStyle(StageStyle.DECORATED);
                    stage.setTitle("Hotel Map");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    System.out.println("Can't open !!!");
                }
            }
        });

    }
    */
    // Sækja morepic glugga
    @FXML
    void morePic(ActionEvent actionEvent) throws IOException {
        System.out.println("Hótel? " + chosenHotel);
        // Loadum nýrri glugga -> MorePic.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MorePic.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MorePicController.class.getName()).log(Level.SEVERE, null, ex);
        }
        MorePicController morePicController = fxmlLoader.getController();
        morePicController.setHotel(chosenHotel);
        morePicController.drawImages();

        Parent root1 = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("More Pictures");
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

        if (roomindex != -1) {
            chosenRoom = (Room) hotelRooms.getItems().get(roomindex);
            System.out.println("Valið herbergi: " + chosenRoom);
            // System.out.println("Herbergi í völdu hóteli: " + chosenHotel.getRooms());
        }

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("services.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }


        servicesController room = Loader.getController();
        //room.setHotel(chosenHotel);
        room.setValues(chosenHotel, chosenRoom, daycountvalue, arrivalchoicevalue, departurechoicevalue, guestnumbervalue);
        room.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
        Parent payment_parent = Loader.getRoot();

        //Parent payment_parent = FXMLLoader.load(getClass().getResource("services.fxml"));
        Scene payment_scene = new Scene(payment_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setTitle("Services");
        main_stage.setScene(payment_scene);
        main_stage.show();
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

    public void showHotel(Hotel hotel) {
        chosenHotel = hotel;
        hotelname.setText(chosenHotel.getName());
        hotelInfo.setText(chosenHotel.getHotelInfo());
        System.out.println("Hótelið er: " + chosenHotel);
        //showRooms();

        // Birtir mynd af völdu hóteli.
        hotelImage.getChildren().clear();
        Integer hotelID = chosenHotel.getHotelID();
        Image image = new Image("is/hi/hbv/utlit/img/Roomimage/hotel/" + hotelID + ".jpg");
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        imageView.setFitWidth(383);
        imageView.setFitHeight(212);
        hotelImage.getChildren().add(imageView);
        morepicID.setTooltip(new Tooltip("See more pictures of "+ chosenHotel.getName() + "."));

        /*
        mapID.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("maplist.fxml"));
                    Parent root = (Parent) loader.load();
                    Stage stage = new Stage();
                    //stage.initStyle(StageStyle.DECORATED);
                    stage.setTitle("Hotel Map");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    System.out.println("Can't open !!!");
                }
            }
        });
        */

    }

    public void setValues(Hotel hotel, long daycount, LocalDate arrival, LocalDate departure, int guests) {
        chosenHotel = hotel;
        daycountvalue = daycount;
        arrivalchoicevalue = arrival;
        departurechoicevalue = departure;
        guestnumbervalue = guests;
        showRooms();
        showHotel(hotel);
    }

    /*
     * Birtir herbergin í völdu hóteli í ListView glugga
     */
    public void showRooms() {
        roomResults = FXCollections.observableArrayList(chosenHotel.getRooms());
        for (Object room : roomResults) {
            room.toString();
        }
        hotelRooms.setItems(roomResults);

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


    public void hotelLocation(ActionEvent actionEvent) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("hotelplace.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        hotelplaceController location = Loader.getController();
        location.setValues(chosenHotel);
        Parent payment_parent = Loader.getRoot();
        Scene payment_scene = new Scene(payment_parent);
        Stage main_stage = new Stage();
        main_stage.setTitle(chosenHotel.getName());
        main_stage.setScene(payment_scene);
        main_stage.setMaximized(true);
        main_stage.show();
    }

}
