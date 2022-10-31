package controller;

import entity.DinnerTable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repository.OrderRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class DinnerTableController implements Initializable {
    @FXML
    GridPane dinnerTableGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dinnerTableGridPane.setHgap(10);
        dinnerTableGridPane.setVgap(10);
        setDinnerTableGridPane();
    }

    public void setDinnerTableGridPane() {

        ObservableList<DinnerTable> dinnerTableList = OrderRepository.getDinnerTable();
        int col = 0;
        int row = 0;

        for (int i = 0; i < dinnerTableList.size(); i++) {
            StackPane stackPane = new StackPane();
            Button button = new Button();
            button.setPrefWidth(120);
            button.setPrefHeight(120);
            DinnerTable dinnerTable = new DinnerTable();
            dinnerTable = dinnerTableList.get(i);

            button.setStyle("-fx-background-radius: 50%;" +
                    "-fx-text-fill: white;" +
                    (dinnerTable.isInUse() ? "-fx-background-color: #3498db" : "-fx-background-color: #95a5a6"));


            button.setText(dinnerTable.getName());
            DinnerTable finalDinnerTable = dinnerTable;
            button.setOnAction((ActionEvent event) -> {
                try {
                    Stage stage = new Stage();
                    stage.setTitle(finalDinnerTable.getName());
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(this.getClass().getResource("../view/Order.fxml"));
                    OrderController controller = new OrderController();
                    controller.setDinnerTable(finalDinnerTable);
                    loader.setController(controller);
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    Stage curStage = (Stage) dinnerTableGridPane.getScene().getWindow();
                    stage.initOwner(curStage);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setOnHidden(e -> {
                        setDinnerTableGridPane();
                    });
                    stage.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            stackPane.getChildren().add(button);
            dinnerTableGridPane.add(stackPane, col, row);
            if (i % 2 == 0) {
                col++;
            } else {
                col = 0;
                row++;
            }
        }
    }
}
