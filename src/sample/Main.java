package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        ((Controller) fxmlLoader.getController()).setStage(primaryStage);
        primaryStage.setTitle("Parcel Comparison");
        primaryStage.setScene(new Scene(root, 800, 675));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
