package Login.Controller;

import Database.DBHandler;
import Login.Model.CurrentUser;
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

public class LoginController {

    public void initialize() {

    }

    @FXML
    private TextField username, password;


    // Handler for the login button
    @FXML
    private void loginBtnAction(ActionEvent event) {
        boolean isAuthenticated = DBHandler.checkLogin(username.getText(), password.getText()); // Check if the given credentials are true

        // Switch to the dashboard view if the given credentials are true
        if(isAuthenticated) {
            CurrentUser.username = username.getText();
            System.out.println("Current user set to: " + CurrentUser.username);

            // Switch the view to the signup window
            Node node=(Node) event.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/Dashboard/View/dashboard.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        // Display error message if credentials are false
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect credentials, please try again");
            alert.setContentText("Username or password is WRONG!!!");

            alert.showAndWait();
            return;

        }
    }

    // Handler for the signup button
    @FXML
    private void signupBtnAction(ActionEvent event) {

        // Switch the view to the signup window
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Login/View/signup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}