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
    public TextArea reviewBox;  // Show reviews for selected hotel

    @FXML
    private Button backID;

    @FXML

    private ListView hotelRooms; // var <String>

    @FXML
    private Label hotelname;    // Show selected hotel name

    @FXML
    private TextArea hotelInfo; // Show hotel-info for selected hotel

    @FXML
    private Button nextID;      // Go to next scene

    @FXML
    private Pane hotelImage;    // Display image for selected hotel

    @FXML
    private Button morepicID;   // Go to morepic scene

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String kennitala;
    private String card;

    int roomindex = -1;

    private Hotel chosenHotel;                  // The chosen hotel from the search
    private Room chosenRoom;                    // The chosen room from last window
    private long daycountvalue;                 // Duration of stay
    private LocalDate arrivalchoicevalue;       // Arrival date
    private LocalDate departurechoicevalue;     // Departure date
    private int guestnumbervalue;               // Number of guests

    private ObservableList<Object> roomResults;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    // Display morePic.fxml
    @FXML
    void morePic(ActionEvent actionEvent) throws IOException {
        System.out.println("Hótel? " + chosenHotel);
        // Load new scene -> MorePic.fxml
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

    // get search scene
    @FXML
    void returnToSearch(ActionEvent actionEvent) throws IOException {
        // Load new scene -> Search.fxml
        // this will remove everything from last input
        Parent Search_parent = FXMLLoader.load(getClass().getResource("Search.fxml"));
        Scene Search_scene = new Scene(Search_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setScene(Search_scene);
        main_stage.show();
    }
    // Farið í þjónustuglugga - ef ekkert herbergi valið kemur viðvörun.
    @FXML
    void selectRoom(ActionEvent actionEvent) throws IOException {

        if (roomindex != -1) {
            chosenRoom = (Room) hotelRooms.getItems().get(roomindex);
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("services.fxml"));
            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            servicesController room = Loader.getController();
            room.setValues(chosenHotel, chosenRoom, daycountvalue, arrivalchoicevalue, departurechoicevalue, guestnumbervalue);
            room.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
            Parent payment_parent = Loader.getRoot();
            Scene payment_scene = new Scene(payment_parent);
            Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            main_stage.setTitle("Services");
            main_stage.setScene(payment_scene);
            main_stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No room chosen");
            alert.setContentText("Please choose a room");
            alert.showAndWait();
        }

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
        ArrayList<String> reviews = chosenHotel.getReviews();
        int count = 1;
        for (String review : reviews) {
            reviewBox.setText(reviewBox.getText() + "Review number " + count + "\n" + review + "\n\n");
            count++;
        }
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
