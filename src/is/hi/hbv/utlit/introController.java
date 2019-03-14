package is.hi.hbv.utlit;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class introController {


    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String kennitala;
    private String card;


    public void setSaveInfo(String Firstname, String Lastname, String Email, String Phone, String Address, String Kennitala, String Card) {
        firstname = Firstname;
        lastname = Lastname;
        email = Email;
        phone = Phone;
        address = Address;
        kennitala = Kennitala;
        card = Card;
    }

    public void searchHandle(javafx.event.ActionEvent actionEvent) {
        // Loadum nýrri senu -> Seach.fxml

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Search.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchController search = Loader.getController();
        search.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
        Parent herbergi_parent = Loader.getRoot();
        Scene herbergi_scene = new Scene(herbergi_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setScene(herbergi_scene);
        main_stage.setMaximized(true);
        main_stage.show();
    }

    public void manageBooking(javafx.event.ActionEvent actionEvent) {
        // Get gildi fra booking number
        // prenta ut :D
    }

    public void hotelMaphandle(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("maplist.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            //stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Hotel Map !");
            stage.setScene(new Scene(root,715,420));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't open !!!");
        }
    }

    public void aboutusHandle(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setHeaderText("About us !!!");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Iceland Hotel");
        alert.setContentText("This project is called Iceland Hotel\n"+
                "This is final project for HBV-401G\n" +
                "Authors : Sunna Björnsdóttir - something@hi.is\n" +
                "Þórdís Pétursdóttir - something@hi.is\n" +
                "Hieu Van Phan - hvp5@hi.is\n" +
                "\n"
        );

        alert.showAndWait();
    }

    public void quitHandle(javafx.event.ActionEvent actionEvent) {
        System.exit(1);
    }
}
