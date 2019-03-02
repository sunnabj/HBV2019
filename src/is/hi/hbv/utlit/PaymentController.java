package is.hi.hbv.utlit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    @FXML
    void Next(ActionEvent actionEvent) {
        if (validateFirstName() && validateLastName()
                            && validateEmaill() && validatePhone()
                            && validateKennitala((getKennitala.getText())) && validateCardNumber()
                            && validateCardExpiryDate() && validateCVC() == true)
        {
            // Loadum nýrri scene -> paymentConfirmation.fxml
            Stage stage123 = (Stage) nextButton.getScene().getWindow();
            stage123.close();
            String Firsname_text = getFirstname.getText();
            String Lastname_text = getLastname.getText();
            String Email_text = getEmail.getText();
            String Phone_text = getPhone.getText();
            String Address_text = getAddress.getText();
            String Kennitala_text = getKennitala.getText();
            String CardNumber_text = getCardnumber.getText();
            String List = totallist.getText();

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("confirm.fxml"));
            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(servicesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            confirmController display = Loader.getController();
            display.setText123(Firsname_text,Lastname_text,Email_text,Phone_text,Address_text,Kennitala_text,CardNumber_text,List);

            Parent p = Loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        }
    }

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

    public void setText12 (String totalslist, String count) {
        int room = 70000;
        int i = Integer.parseInt(count);
        int x = room+i;
        totalscost.setText("Totals cost : " + x +" kr.");
        totallist.setText("----Room Infomation----\n"
                                    + "Day/s - Roomtype - Price \n"
                                    + " 1 - Kingsize - 70.000 kr \n" + "\n"
                                    + "----Extra Services----\n"+ totalslist + "\n");
    }

    public void saveInfosetText (TextField card, TextField expiry, TextField CVC) {
        getCardnumber.setText(String.valueOf(card));
        getExpirydate.setText(String.valueOf(expiry));
        getCVC.setText(String.valueOf(CVC));
    }

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
}