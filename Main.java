import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Database.DBHandler;

public class Main extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        Parent root = FXMLLoader.load(Main.class.getResource("Login/View/login.fxml"));
        primaryStage.setTitle("Personal Finance Software");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        DBHandler.initializeDatabase(); // Initialize the database, create tables

    }



    public static void main(String[] args) {
        launch(args);
    }
}
