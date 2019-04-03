package is.hi.hbv.utlit;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class introController implements Initializable {


    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String kennitala;
    private String card;

    @FXML
    private Button aboutus;

    @FXML
    private Button searchID;

    @FXML
    private Button locationID;

    @FXML
    private Button bookingID;

    @FXML
    private Button quitID;


    /*
    * The payment information are set if the user has chosen to save it after having ordered a hotel before
     */
    public void setSaveInfo(String Firstname, String Lastname, String Email, String Phone, String Address, String Kennitala, String Card) {
        firstname = Firstname;
        lastname = Lastname;
        email = Email;
        phone = Phone;
        address = Address;
        kennitala = Kennitala;
        card = Card;
    }

    /*
    * Loads the search scene
     */
    public void searchHandle(javafx.event.ActionEvent actionEvent) {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Search.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchController search = Loader.getController();
        search.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
        Parent search_parent = Loader.getRoot();
        Scene search_scene = new Scene(search_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setScene(search_scene);
        main_stage.setMaximized(true);
        main_stage.show();
    }

    /*
    * Loads a window where the user can take a look at a certain booking confirmation, based on
    * its booking ID
     */
    public void manageBooking(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("booking.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Log-in with BookID");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't open !!!");
        }
    }

    /*
    * Loads a map of Iceland, showing main areas where hotels can be found
     */
    public void hotelMaphandle(javafx.event.ActionEvent actionEvent) {
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

    /*
    * Opens an information window about the project and authors
     */
    public void aboutusHandle(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setHeaderText("About us");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Iceland Hotel");
        alert.setContentText("This project is called Hotels of Iceland\n"+
                "This is final project for HBV-401G\n" +
                "Authors : Sunna Björnsdóttir - sub4@hi.is\n" +
                "Þórdís Pétursdóttir - thp44@hi.is\n" +
                "Hieu Van Phan - hvp5@hi.is\n" +
                "\n"
        );
        alert.showAndWait();
    }

    public void quitHandle(javafx.event.ActionEvent actionEvent) {
        System.exit(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchID.setTooltip(new Tooltip("Search for a hotel"));
        bookingID.setTooltip(new Tooltip("Go to manage your booking"));
        locationID.setTooltip(new Tooltip("See the locations of our hotels around Iceland"));
        aboutus.setTooltip(new Tooltip("About this application"));
        quitID.setTooltip(new Tooltip("Quit this application"));
    }
}
