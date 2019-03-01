package is.hi.hbv.utlit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import java.io.File;
import java.net.URL;


public class MorePicController implements Initializable {

    @FXML
    private ScrollPane imageSlider;

    @FXML
    void tilBaka(ActionEvent actionEvent) throws IOException {
        // Loadum aftur scene -> herbergi.fxml
        Parent herbergi_parent = FXMLLoader.load(getClass().getResource("herbergi.fxml"));
        Scene herbergi_scene = new Scene(herbergi_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setTitle("Room infomation");
        main_stage.setScene(herbergi_scene);
        main_stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File folder = new File("resources/img");
        File[] listOfFiles = folder.listFiles();
        TilePane tilePane = new TilePane();
        tilePane.setHgap(0);
        tilePane.setVgap(0);
        tilePane.setMaxWidth(1120);
        for (int i = 0; i < listOfFiles.length; i++) {
            Image img = new Image(listOfFiles[i].toURI().toString());
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(400);
            imageView.setFitWidth(560);
            imageView.setPreserveRatio(true);
            tilePane.getChildren().addAll(imageView);
        }
       imageSlider.setContent(tilePane);
    }

}
