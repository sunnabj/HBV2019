package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.Hotel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class confirmController implements Initializable {

    @FXML
    private AnchorPane Reservation;

    @FXML
    private Label resultBooking;

    @FXML
    private Label resultInfo;

    @FXML
    private Label getName;

    @FXML
    private ImageView hotelImage1;


    @FXML
    private Button saveButton;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String kennitala;
    private String card;
    Connection connection= null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setHeaderText("Payment successful!");
        alert.setContentText("Your payment has been verified\n"+
                "Here are further details");
        alert.showAndWait() ;


        saveButton.setTooltip(new Tooltip("Save this booking and continue/quit"));
    }
    String book = bookingNumber();
    String pin = pinNumber();

    // Þetta sækja gögnum af herbergi sem notenda hefur valið og birta á
    // þarf samt biða eftir gögnum og helst ekki vinna strax því getur árekstur á Search.fxml eða HotelDAO....
    public void setText12 (Hotel chosenHotel, long daycountvalue, LocalDate arrivalchoicevalue,
                           LocalDate departurechoicevalue, int guestnumbervalue) {
        getName.setText(chosenHotel.getName() + " , "+ chosenHotel.getStars() + " stars."+"\n" +
                            "Address : " + chosenHotel.getHotelAddress() + "\n" +
                            "Phone number : (+354) " + chosenHotel.getPhoneNr() + "\n"
        );
        resultBooking.setText("-------------Booking Information--------------\n\n" +
                            "Number of night(s) : " + daycountvalue + " day(s)" + "\n" +
                            "Arrival : " + arrivalchoicevalue + "\n" +
                            "Departure : " + departurechoicevalue + "\n" +
                            "Booking number : " + book + "\n" +
                            "Booking-pin number : " + pin + "\n" +
                            "Number of guests : " + guestnumbervalue + "\n"
                );

        // Birtir mynd af völdu hóteli.
        Integer hotelID = chosenHotel.getHotelID();
        Image image = new Image("is/hi/hbv/utlit/img/Roomimage/hotel/" + hotelID + ".jpg");
        hotelImage1.setImage(image);
    }

    // Þetta er að ferð kalla allar upplýsingar frá Paymentglugga og prenta það út ...
    // Ef getur, getur sameina aðferð uppí í þetta það sé snyrtilega xD.
    public void setText123 (String Firstname,String Lastname,String Email,String Phone,
                            String Address,String Kennitala,String Card, int price, int addedPrice) {
        int roomPrice = price - addedPrice;
        if (Address == null) {Address = "";}
        resultInfo.setText( "-------------------Total Cost----------------\n\n" +
                            "Room : " + roomPrice + " ISK" + "\n" +
                            "Additional services : " + addedPrice + " ISK" + "\n" +
                            "Total : " + price + " ISK" + "\n\n" +
                            "---------------Guest Information--------------\n\n" +
                            "Guest Name : Mr/Mrs." + Firstname + "\n" +
                            "Last Name : " + Lastname + "\n" +
                            "Email : " + Email + " \n" +
                            "Phone nr. : " + Phone + "\n" +
                            "Address : " + Address + "\n" +
                            "Kennitala : " + Kennitala + "\n" +
                            "Card-number : " + Card + "\n" +
                            "\n" + "=======================================" + "\n" +
                            "Do you have any problems with this program ?" + "\n" +
                            "Contact us : Iceland@hotel.com or \n" +
                            "Tel : +354 6590792, +354 6946636, +354 6161350"
        );
        firstname = Firstname;
        lastname = Lastname;
        email = Email;
        phone = Phone;
        address = Address;
        kennitala = Kennitala;
        card = Card;
    }
    // Save button action control kalla Snapshot af glugga sem er í (confirm glugga) og vista
    // í hvar sem er inn á tölva notenda. Kalla Continue aðferð dialog
    @FXML
    void nextPage(ActionEvent actionEvent) throws IOException, SQLException {
        saveImage();
        Continue(actionEvent);
        savetoSQL(book,pin);
    }
    // Vista snapshot af Confirm glugga og vista á notenda tölvar.
    private void saveImage() throws IOException {
        Stage stage123 = (Stage) saveButton.getScene().getWindow();
        Image i = Reservation.snapshot(null, null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showSaveDialog(stage123);
        // vista fyrir notenda tolvu
        if (file != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(i, null), "png", file);
        }

        //vista í resource mappa til að forrit geta sækja seinna bóoking og afbóka :D
        ImageIO.write(SwingFXUtils.fromFXImage(i, null), "png",
                new File("src/is/hi/hbv/utlit/img/Roomimage/booking/"+book+".png"));

    }

    // Continue dialog staðfest til láta notenda velur valmöguleika Yes þá til baka á search ...
    // No þá hætta við forrit ....
    private void Continue(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Your booking has been confirmed!");
        alert.setContentText("Thank you for booking with Hotels of Iceland.\n"+
                "Do you want to book another one ?");
        alert.resultProperty().addListener((observable, previous, current) -> {
            if (current == ButtonType.YES) {
                try {
                    saveInfo(actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (current == ButtonType.NO) {
                System.exit(1);
            }
        });
        alert.show();
    }


    // Þetta þarf laga betra til að virka
    // hugmynd að kalla á textfield eins og sækja gildi frá payment í confirm ... þarf tíma ...
    public void saveInfo(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Save Card-infomation !!!");
        alert.setContentText("Do you want to save your\n"+
                "Card-infomation for next booking ?");
        alert.resultProperty().addListener((observable, previous, current) -> {
            if (current == ButtonType.YES) {
                // Loadum nýrri senu -> Seach.fxml

                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("Intro.fxml"));
                try {
                    Loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //searchController search = Loader.getController();
                //search.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
                introController intro = Loader.getController();
                intro.setSaveInfo(firstname, lastname, email, phone, address, kennitala, card);
                Parent herbergi_parent = Loader.getRoot();
                Scene herbergi_scene = new Scene(herbergi_parent);
                Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                main_stage.setScene(herbergi_scene);
                main_stage.show();
            } else if (current == ButtonType.NO) {
                Parent herbergi_parent = null;
                try {
                    herbergi_parent = FXMLLoader.load(getClass().getResource("Intro.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene herbergi_scene = new Scene(herbergi_parent);
                Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                main_stage.setScene(herbergi_scene);
                main_stage.show();
            }
        });
        alert.showAndWait();
    }


    // Generate 10 random number fyrir booking numer
    public String bookingNumber() {
        Random book1 = new Random();
        int random;
        String m[] = new String[10];
        for (int i = 0; i<10; i++) {
            random = book1.nextInt(10);
            m[i] = Integer.toString(random);
        }
        return m[0]+m[1]+m[2]+m[3]+m[4]+m[5]+m[6]+m[7]+m[8]+m[9];
    }

    // Generate 4 random number fyrir booking numer
    public String pinNumber() {
        Random book1 = new Random();
        int random;
        String m[] = new String[4];
        for (int i = 0; i<4; i++) {
            random = book1.nextInt(4);
            m[i] = Integer.toString(random);
        }
        return m[0]+m[1]+m[2]+m[3];
    }


    public void savetoSQL(String bookingID, String pinID) {
        String sql = "INSERT INTO Booking(BookingID,PinID) VALUES(?,?)";
        String url = "jdbc:sqlite:hotels.db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookingID);
            pstmt.setString(2, pinID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage()+" help me");
        }
    }
}
