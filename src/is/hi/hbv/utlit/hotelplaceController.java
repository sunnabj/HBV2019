package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.Hotel;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.time.LocalDate;

public class hotelplaceController{
    @FXML
    private WebView mapView;
    private Hotel chosenHotel;

    public void setValues(Hotel hotel) {
        chosenHotel = hotel;
        WebEngine engine = mapView.getEngine();
        engine.load("https://www.google.com/maps/search/"+chosenHotel.getHotelAddress());
    }
}
