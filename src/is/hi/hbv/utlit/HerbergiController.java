package is.hi.hbv.utlit;
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
import javafx.stage.Stage;
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


public class HerbergiController {
    @FXML
    private MenuItem pin_info;

    @FXML
    private Slider zoom_slider;

    @FXML
    private ScrollPane map_scrollpane;

    @FXML
    private MenuButton map_pin;

    Group zoomGroup;

    // Sækja morepic glugga
    @FXML
    void morePic(ActionEvent actionEvent) throws IOException {
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
        Parent payment_parent = FXMLLoader.load(getClass().getResource("services.fxml"));
        Scene payment_scene = new Scene(payment_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setTitle("Services");
        main_stage.setScene(payment_scene);
        main_stage.show();
    }


    @FXML
    void initialize() {

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


}
