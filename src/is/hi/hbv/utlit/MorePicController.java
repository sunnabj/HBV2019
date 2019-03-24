package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.Hotel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MorePicController implements Initializable {

    @FXML
    private ScrollPane imageSlider;


    @FXML
    void tilBaka(ActionEvent actionEvent) throws IOException {
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.close();
    }

    private Hotel chosenHotel;
    
    public void setHotel(Hotel hotel) {
        chosenHotel = hotel;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void drawImages(){
        File folder = new File("resources/img/" + chosenHotel.getHotelID());
        File[] listOfFiles = folder.listFiles();
        TilePane tilePane = new TilePane();
        tilePane.setHgap(0);
        tilePane.setVgap(0);
        tilePane.setMaxWidth(1120);
        for (File listOfFile : listOfFiles) {
            Image img = new Image(listOfFile.toURI().toString());
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(400);
            imageView.setFitWidth(560);
            imageView.setPreserveRatio(true);
            tilePane.getChildren().addAll(imageView);
        }
        imageSlider.setContent(tilePane);
    }

}
