package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Test extends Application {
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        Parent root = null;
        root = new FXMLLoader().load(this.getClass().getResource("../view/DinnerTable.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
