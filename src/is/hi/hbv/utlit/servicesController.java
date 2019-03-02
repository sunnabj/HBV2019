package is.hi.hbv.utlit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
    private Button nextButton;

    private String a;
    private String b;

    @FXML
    void nextPage(ActionEvent actionEvent) {
        Stage stage123 = (Stage) nextButton.getScene().getWindow();
        stage123.close();
        String list_text = b;
        String cost_text = a;

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("payment.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PaymentController display = Loader.getController();
        display.setText12(list_text,cost_text);

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
    }

    public void setCount(String s)
    {
        a= s;
    }

    public String getCount()
    {
        return a;
    }

    public void setChoice(String s)
    {
        b= s;
    }

    public String getChoice()
    {
        return b;
    }

    void checkbox(){
        int count = 0;
        String choice = "";
        if (pickup.isSelected()) {
            count+=2500;
            choice += pickup.getText()+ " 2500 kr\n";
        }

        if (breakfast.isSelected()) {
            count+=1980;
            choice += breakfast.getText()+ " 1980 kr\n";
        }

        if (dinner.isSelected()) {
            count+=1780;
            choice += dinner.getText()+ " 1780 kr\n";
        }

        if (rental.isSelected()) {
            count+=4500;
            choice += rental.getText()+ " 4500 kr\n";
        }

        if (spa.isSelected()) {
            count+=7500;
            choice += spa.getText()+ " 7500 kr\n";
        }

        if (swimming.isSelected()) {
            count+=998;
            choice += swimming.getText()+ " 998 kr\n";
        }
        totalscost.setText("Totals cost : " + count +" kr.");
        totalslist.setText(choice);
        setCount(String.valueOf(count));
        setChoice(choice);
    }

    @FXML
    void handleButtonAction() {
        checkbox();
    }
}
