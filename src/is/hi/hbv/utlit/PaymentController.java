package is.hi.hbv.utlit;

import is.hi.hbv.vinnsla.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


import javax.lang.model.SourceVersion;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class PaymentController<fxmlLoader> {

    @FXML
    private TextField getPhone;         // Textfield til að sækja gildi fyrir símanúmer

    @FXML
    private TextField getCVC;           // Textfield til að sækja gildi fyrir CVC

    @FXML
    private TextField getFirstname;     // Textfield til að sækja gildi fyrir Nafn

    @FXML
    private TextField getLastname;      // Textfield til að sækja gildi fyrir Last-nafn

    @FXML
    private TextField getKennitala;     // Textfield til að sækja gildi fyrir kennitölur

    @FXML
    private TextField getExpirydate;    // Textfield til að sækja gildi fyrir rennurúttími á kort

    @FXML
    private TextField getEmail;         // Textfield til að sækja gildi fyrir netfang

    @FXML
    private TextField getAddress;

    @FXML
    private Button nextButton;

    @FXML
    private Label totalscost;

    @FXML
    private Label totallist;

    @FXML
    private TextField getCardnumber;    // Textfield til að sækja gildi fyrir kortnúmer

    private Hotel chosenHotel;
    private long daycountvalue;
    private LocalDate arrivalchoicevalue;
    private LocalDate departurechoicevalue;
    private int guestnumbervalue;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String kennitala;
    private String card;

    // Next button sækja nokkra hluta og athuga hvort það er satt eða ósatt
    @FXML
    void Next(ActionEvent actionEvent) {
        if (validateFirstName() && validateLastName()                                                   // if-setning athuga fyrir hvert fall er
                            && validateEmaill() && validatePhone()                                      // það satt eða ósatt ef satt halda áfram
                            && validateKennitala((getKennitala.getText())) && validateCardNumber()
                            && validateCardExpiryDate() && validateCVC() == true)
        {
            // Loadum nýrri scene -> paymentConfirmation.fxml
            // Loka fyrst gamla glugga með nextButton til að sækja núverandi scene
            Stage stage123 = (Stage) nextButton.getScene().getWindow();
            // Svo lokað gamla scene
            stage123.close();
            // Sækja texta frá nokkra Textfield innan scene.
            String Firsname_text = getFirstname.getText();
            String Lastname_text = getLastname.getText();
            String Email_text = getEmail.getText();
            String Phone_text = getPhone.getText();
            String Address_text = getAddress.getText();
            String Kennitala_text = getKennitala.getText();
            String CardNumber_text = getCardnumber.getText();
            String List = totallist.getText();

            // Aðferð að kalla gögna frá annara scene, ekki saman við aðra aðferð.
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("confirm.fxml"));
            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            confirmController display = Loader.getController();
            display.setText123(Firsname_text,Lastname_text,Email_text,Phone_text,Address_text,Kennitala_text,CardNumber_text,List);
            //display.setTextHotel(chosenHotel);
            display.setText12(chosenHotel, daycountvalue, arrivalchoicevalue, departurechoicevalue, guestnumbervalue);
            //display.setSaveInfo(Firstname, Lastname, Email, Phone, Address, Kennitala, Card);
            Parent p = Loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        }
    }

    // Þetta bara venjulegt aðferð til að kalla á nýju scene
    @FXML
    void Back (ActionEvent actionEvent) throws IOException {
        // Loadum nýrri scene -> herbergi.fxml
        Parent MorePic_parent = FXMLLoader.load(getClass().getResource("herbergi.fxml"));
        Scene Search_scene = new Scene(MorePic_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        main_stage.setTitle("Room infomation");
        main_stage.setScene(Search_scene);
        main_stage.show();
    }

    // bara eitthvað fall til að athuga hvort þetta er int eða ekki ... nota ekki núna
    private boolean isInt(TextField input, String message) {
        try {
            int number = Integer.parseInt(getCardnumber.getText());
            System.out.println("Number is valid: "+ number);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error: "+message + " is invalid");
            return false;
        }
    }

    // Athuga Card hvort þetta legit eða ekki .... sækja ekki upplýsingar frá þeim :D
    private boolean validateCardNumber(){
        Pattern p = Pattern.compile("^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
                                            "(?<mastercard>5[1-5][0-9]{14})|" +
                                            "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
                                            "(?<amex>3[47][0-9]{13})|" +
                                            "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
                                            "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$");
        Matcher m = p.matcher(getCardnumber.getText());
        if(m.find() && m.group().equals(getCardnumber.getText())){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Card-number");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Card-number");
            alert.showAndWait();
            return false;
        }
    }

    // Athuga hvort úttrunni tíma er til ??
    private boolean validateCardExpiryDate() {
        Pattern p = Pattern.compile("(?:0[1-9]|1[0-2])/[0-9]{2}");
        Matcher m = p.matcher(getExpirydate.getText());
        if(m.find() && m.group().equals(getExpirydate.getText())){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Expiry date");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Expiry date");
            alert.showAndWait();
            return false;
        }
    }

    // Athuga CVC ....
    private boolean validateCVC() {
        Pattern p = Pattern.compile("[1-9][1-9][1-9]");
        Matcher m = p.matcher(getCVC.getText());
        if(m.find() && m.group().equals(getCVC.getText())){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate CVC");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid CVC");
            alert.showAndWait();
            return false;
        }
    }

    // Athuga Email ..... nota regex til að athuga
    private boolean validateEmaill(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(getEmail.getText());
        if(m.find() && m.group().equals(getEmail.getText())){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Email");
            alert.showAndWait();
            return false;
        }
    }

    // Nota Regex til að athuga hvort það satt ??
    private boolean validateFirstName(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(getFirstname.getText());
        if(m.find() && m.group().equals(getFirstname.getText())){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate First Name");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid First Name");
            alert.showAndWait();
            return false;
        }
    }

    //  Athuga lastname ... en saman við first name ... bara má ekki hafa tölur inn á
    private boolean validateLastName(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(getLastname.getText());
        if(m.find() && m.group().equals(getLastname.getText())){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Last Name");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Last Name");
            alert.showAndWait();

            return false;
        }
    }

    // Athuga kennitala sé rétt eða ekki .... þetta hefur lærð á Tölvunarfræði 1
    private boolean validateKennitala(String a){
        int lengd = getKennitala.getText().length();
        System.out.println(a);
        int j = 3;
        int k = 0;
        if (lengd != 10 ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Kennitala");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Kennitala");
            alert.showAndWait();
            return false;
        } else {
            for (int i = 0; i < lengd; i++){
                int x = Character.getNumericValue(a.charAt(i));
                k += x*j;
                j--;
                if (j == 1)
                    j = 7;
            }
            int mod = k%11;
            if (mod >=0 && mod <=9){
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate Kennitala");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter Valid Kennitala");
                alert.showAndWait();
                return false;
            }
        }
    }

    // Athuga hvort símanúmer sé rétt á íslenska síma
    private boolean validatePhone(){
        Pattern p = Pattern.compile("(00354|354)?[5-8][0-9][0-9][0-9][0-9][0-9][0-9]");
        Matcher m = p.matcher(getPhone.getText());
        if(m.find() && m.group().equals(getPhone.getText())){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Mobile Number");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Mobile Number");
            alert.showAndWait();
            return false;
        }
    }

    // TODO : þetta þarf að laga/breyta því þarf að sækja gögnum á gagnasafn
    // og þetta á að sækja herbergi upplýsingar !!!
    public void setText12 (String totalslist, String count) {
        int room = 70000;
        int i = Integer.parseInt(count);
        int x = room+i;
        totalscost.setText("Totals cost : " + x +" kr.");
        totallist.setText("----Room Infomation----\n"
                                    + "Day/s - Roomtype - Price \n"
                                    + " 1 - Kingsize - 70.000 kr \n" + "\n"
                                    + "----Extra Services----\n"+ totalslist + "\n");
        System.out.println("Valið hótel í paymentController: " + chosenHotel);
    }

    // bara testa hvort þetta sé virka ef maður búinn á reservation og langar vista korta upplýsingar ...
    public void saveInfosetText (TextField card, TextField expiry, TextField CVC) {
        getCardnumber.setText(String.valueOf(card));
        getExpirydate.setText(String.valueOf(expiry));
        getCVC.setText(String.valueOf(CVC));
    }

    // þetta líka
    public void saveInfogetText (String card, String expiry, String CVC) {
        TextField card1 = new TextField(), expiry1 = new TextField(),CVC1 = new TextField();
        String card_Text = card1.getText();
        String expiry_Text = expiry1.getText();
        String cvc_Text = CVC1.getText();
        getCardnumber.getText();
        getExpirydate.getText();
        getCVC.getText();
        FXMLLoader Loader = new FXMLLoader();
        confirmController display = Loader.getController();
        //display.saveInfosetText (card, expiry, CVC);
    }

    public void setValues(Hotel hotel, long daycount, LocalDate arrival, LocalDate departure, int guests) {
        chosenHotel = hotel;
        daycountvalue = daycount;
        arrivalchoicevalue = arrival;
        departurechoicevalue = departure;
        guestnumbervalue = guests;
    }

    public void setSaveInfo(String Firstname, String Lastname, String Email, String Phone, String Address, String Kennitala, String Card) {
        this.getFirstname.setText(Firstname);
        this.getLastname.setText(Lastname);
        this.getEmail.setText(Email);
        this.getPhone.setText(Phone);
        this.getAddress.setText(Address);
        this.getKennitala.setText(Kennitala);
        this.getCardnumber.setText(Card);
    }
}