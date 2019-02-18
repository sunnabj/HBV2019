package is.hi.hbv.utlit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MailListController implements Initializable {

    @FXML
    private AnchorPane nmailDialog;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void mailDialog() {
        // Innihald dialogs búið til
        DialogPane p = new DialogPane();
        nmailDialog.setVisible(true);

        // Innihald sett sem Pane sem fengið er úr Scene builder
        p.setContent(nmailDialog);

        // Umgjörðin búin til
        Dialog<ButtonType> d = new Dialog();

        // og innihaldið sett í umgjörðina
        d.setDialogPane(p);
        // Haus og titill
        d.setTitle("Thank you for registering!");

        // Hnappur til að loka glugga búinn til og bætt við
        ButtonType close = new ButtonType("Close",
                ButtonBar.ButtonData.CANCEL_CLOSE);
        d.getDialogPane().getButtonTypes().add(close);

        // Dialog birtur - svarið ekki notað
        d.showAndWait();
    }
}
