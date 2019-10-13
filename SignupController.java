package Login.Controller;

import Database.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class SignupController {

    public void initialize() {

    }




    @FXML
    private TextField username, password, age,gender, lira, usd, aud, dkk, eur, gbp, chf, sek, cad, kwd, nok, sar, jpy, bgn, ron, rub, irr, cny, pkr, qar; //



    // Handler for the signup button
    @FXML
    private void signupBtnAction(ActionEvent event) {

        // Pop up error if any of the inputs are null
        if(username.getText().isEmpty() || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect credentials, please try again");
            alert.setContentText("Username and password can't be empty");

            alert.showAndWait();
            return;
        }
        //
        if(Integer.parseInt(lira.getText().toString()) + Integer.parseInt(usd.getText().toString()) + Integer.parseInt(eur.getText().toString()) + Integer.parseInt(gbp.getText().toString()) + Integer.parseInt(sek.getText()) + Integer.parseInt(cad.getText().toString())+ Integer.parseInt(dkk.getText().toString())+ Integer.parseInt(chf.getText().toString())+ Integer.parseInt(kwd.getText().toString()) + Integer.parseInt(nok.getText().toString())+ Integer.parseInt(sar.getText().toString())+ Integer.parseInt(sar.getText().toString())+ Integer.parseInt(jpy.getText().toString())+ Integer.parseInt(bgn.getText().toString())+ Integer.parseInt(ron.getText().toString())+ Integer.parseInt(rub.getText().toString())+ Integer.parseInt(irr.getText().toString())+ Integer.parseInt(cny.getText().toString())+ Integer.parseInt(pkr.getText().toString())+ Integer.parseInt(qar.getText().toString())<= 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect credentials, please try again");
            alert.setContentText("A user must have initial balance in at least ONE currency");

            alert.showAndWait();
            return;

        }

        // Fetch the inputs from the textFields, then create a user on the database with those

        DBHandler.createUser(username.getText(), password.getText(), Integer.parseInt(age.getText()), gender.getText(), lira.getText(), usd.getText(), aud.getText(), dkk.getText(), eur.getText(), gbp.getText(), chf.getText(), sek.getText(), cad.getText(),kwd.getText(),nok.getText(),sar.getText(),jpy.getText(),bgn.getText(),ron.getText(),rub.getText(),irr.getText(),cny.getText(),pkr.getText(),qar.getText());
        System.out.println("Created user with username: " + username.getText());


        // Switch the view to the login window
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Login/View/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void mainMenuBtnAction(ActionEvent event) {
        // Switch the view to the login window
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Login/View/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}