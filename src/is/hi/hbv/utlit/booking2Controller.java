package is.hi.hbv.utlit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.*;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class booking2Controller implements Initializable {

    @FXML
    private ImageView imageID;

    @FXML
    private Button okID;

    @FXML
    private Button cancelBooking;
    private String booking;

    //String img = "is/hi/hbv/utlit/img/Roomimage/boo0king/" + booking + ".png";
    //Image image = new Image("is/hi/hbv/utlit/img/Roomimage/booking/" + booking + ".png");
    public void setText12 (String BookingID, String PinID) {
        Image image = new Image("is/hi/hbv/utlit/img/Roomimage/booking/" + BookingID + ".png");
        imageID.setImage(image);
        booking = BookingID;
    }


    public void cancelBookingID(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Warning !");
        alert.setContentText("Are you sure you want to cancel this reversation ?");

        String text = "Please restart application.";
        File input = new File("src/is/hi/hbv/utlit/img/Roomimage/booking/" + booking + ".png");
        File output = new File("src/is/hi/hbv/utlit/img/Roomimage/booking/" + booking + ".png");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            addTextWatermark(text, "png", input, output);
        }

        //  Ætlar bara eyðar booking myndir fyrst EN fann hinn aðferð geggjað !!!
        //  þetta samt virka mjög vel
        //
        //Optional<ButtonType> result = alert.showAndWait();
        //if (result.get() == ButtonType.OK) {
        //    deleteDir(new File( "src/is/hi/hbv/utlit/img/Roomimage/booking/" + booking + ".png"));
        //}
        // adding text as overlay to an image
        alert();

        //Display new image that say restart application
        Image image = new Image("is/hi/hbv/utlit/img/Roomimage/booking/cancelled.png");
        imageID.setImage(image);
    }

    //  Delete file method that work but i decide to use add watermark instead.
    public static boolean deleteDir(File dir) {
        return dir.delete();
    }

    // Watermark method that can draw in image
    private static void addTextWatermark(String text, String type, File source, File destination) throws IOException {
        BufferedImage image = ImageIO.read(source);

        // determine image type and handle correct transparency
        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

        // initializes necessary graphic properties
        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(image, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        w.setComposite(alphaChannel);
        w.setColor(Color.RED);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        FontMetrics fontMetrics = w.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, w);

        // calculate center of the image
        int centerX = (image.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = image.getHeight() / 2;

        // add text overlay to the image
        w.drawString(text, centerX, centerY);
        ImageIO.write(watermarked, type, destination);
        w.dispose();
    }

    // Close current window
    public void closeAction(ActionEvent actionEvent) {
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        okID.setTooltip(new Tooltip("Close this window"));
        cancelBooking.setTooltip(new Tooltip("Cancel this reservation"));
    }

    public void alert () {
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert2.setHeaderText("Tip");
        alert2.setContentText("For better user experiment\n"+
                            "Please restart this application before " +
                            "check your booking");
        alert2.showAndWait();
    }
}
