package is.hi.hbv.utlit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


import javax.lang.model.SourceVersion;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class PaymentController {

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
    private TextField getCardnumber;    // Textfield til að sækja gildi fyrir kortnúmer

    @FXML
    private Button nextButton;          // Takkið next til að fara næsta scene

    @FXML
    void Next(ActionEvent event) {
        nextButton.setOnAction( e -> {
            validateFirstName();
            validateLastName();
            validateEmaill();
            validatePhone();
            validateKennitala((getKennitala.getText()));
            validateCardNumber();
            validateCardExpiryDate();
            validateCVC();
        });
    }

    @FXML
    void Back (ActionEvent actionEvent) throws IOException {
        // Loadum nýrri scene -> MorePic.fxml
        Parent MorePic_parent = FXMLLoader.load(getClass().getResource("herbergi.fxml"));
        Scene Search_scene = new Scene(MorePic_parent);
        Stage main_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Card-number");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Card-number");
            alert.showAndWait();
            return false;
        }
    }
    boolean validateCardExpiryDate() {
        Pattern p = Pattern.compile("(?:0[1-9]|1[0-2])/[0-9]{2}");
        Matcher m = p.matcher(getExpirydate.getText());
        if(m.find() && m.group().equals(getExpirydate.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Expiry date");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Expiry date");
            alert.showAndWait();
            return false;
        }
    }

    boolean validateCVC() {
        Pattern p = Pattern.compile("[1-9][1-9][1-9]");
        Matcher m = p.matcher(getCVC.getText());
        if(m.find() && m.group().equals(getCVC.getText())){
            return true;
        }else{
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
        }else{
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
        }else{
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
        }else{
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
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Mobile Number");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Mobile Number");
            alert.showAndWait();
            return false;
        }
    }

}