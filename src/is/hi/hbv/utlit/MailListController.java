package is.hi.hbv.utlit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MailListController implements Initializable {

    @FXML
    private AnchorPane nmailDialog;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void mailDialog(String mailID) {
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

        String sql = "INSERT INTO Mail(Maillist) VALUES(?)";
        String url = "jdbc:sqlite:hotels.db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mailID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage()+" help me");
        }

        // Hnappur til að loka glugga búinn til og bætt við
        ButtonType close = new ButtonType("Close",
                ButtonBar.ButtonData.CANCEL_CLOSE);
        d.getDialogPane().getButtonTypes().add(close);

        // Dialog birtur - svarið ekki notað
        d.showAndWait();
    }
}
