package is.hi.hbv.utlit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class servicesController {

    @FXML
    private CheckBox swimming;

    @FXML
    private CheckBox spa;

    @FXML
    private Label totalslist;

    @FXML
    private CheckBox pickup;

    @FXML
    private Label totalscost;

    @FXML
    private CheckBox breakfast;

    @FXML
    private CheckBox dinner;

    @FXML
    private CheckBox rental;

    @FXML
    void nextPage(ActionEvent actionEvent) throws IOException {
        handleButtonAction();
        // Loadum nýrri scene -> payment.fxml
        Parent payment_parent = FXMLLoader.load(getClass().getResource("payment.fxml"));
        Scene payment_scene = new Scene(payment_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setTitle("Payment");
        main_stage.setScene(payment_scene);
        main_stage.show();
    }


    @FXML
    void handleButtonAction() {
        int count = 0;
        String choice = "";
        if (pickup.isSelected()) {
            count+=2500;
            choice += pickup.getText()+ " 2500 kr\n";
        }

        if (breakfast.isSelected()) {
            count+=1980;
            choice += breakfast.getText()+ " 1980 kr \n";
        }

        if (dinner.isSelected()) {
            count+=1780;
            choice += dinner.getText()+ " 1780 kr \n";
        }

        if (rental.isSelected()) {
            count+=4500;
            choice += rental.getText()+ " 4500 kr \n";
        }

        if (spa.isSelected()) {
            count+=7500;
            choice += spa.getText()+ " 7500 kr \n";
        }

        if (swimming.isSelected()) {
            count+=998;
            choice += swimming.getText()+ " 998 kr \n";
        }
        totalscost.setText("Totals cost : " + count +" kr.");
        totalslist.setText(choice);
    }
}
